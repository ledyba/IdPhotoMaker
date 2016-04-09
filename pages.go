package main

import (
	"fmt"
	"log"
	"net/http"
	"strconv"
	"time"

	"github.com/ledyba/IdPhotoMaker/pdf"
	"github.com/ledyba/IdPhotoMaker/photo"
)

func mainHandler(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path != "/" {
		return
	}
	type Data struct {
		Title string
	}
	dat := Data{
		Title: "",
	}
	render("index", dat, w, r)
}

func uploadHandler(w http.ResponseWriter, r *http.Request) {
	var err error
	type Data struct {
		Title   string
		Message string
	}
	err = r.ParseMultipartForm(32 * 1024 * 1024)
	if err != nil {
		dat := Data{
			Title:   "写真のアップロードエラー",
			Message: fmt.Sprintf("画像の読み込みエラー：%s", err.Error()),
		}
		render("upload-error", dat, w, r)
		return
	}
	fileMap := r.MultipartForm.File
	files, found := fileMap["data"]
	if !found || len(files) <= 0 {
		dat := Data{
			Title:   "写真のアップロードエラー",
			Message: "画像がアップロードされていません",
		}
		render("upload-error", dat, w, r)
	}
	file := files[0]
	ph, err := photo.NewPhoto(file)
	if err != nil {
		dat := Data{
			Title:   "写真のアップロードエラー",
			Message: fmt.Sprintf("画像の読み込みエラー：%s", err.Error()),
		}
		render("upload-error", dat, w, r)
		return
	}
	photo.Register(ph)
	{
		type Data struct {
			Title       string
			Secret      string
			ThumbWidth  int
			ThumbHeight int
		}
		dat := Data{
			Title:       "印刷設定",
			Secret:      ph.Secret,
			ThumbWidth:  ph.ThumbWidth,
			ThumbHeight: ph.ThumbHeight,
		}
		render("upload", dat, w, r)
	}
}

func thumbHandler(w http.ResponseWriter, r *http.Request) {
	secret := r.URL.Query().Get("id")
	ph := photo.Fetch(secret)
	if ph == nil {
		w.WriteHeader(404)
		return
	}
	w.Header().Set("Content-Type", "image/jpeg")
	w.Write(ph.Thumb)
}

func pdfHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	var err error
	form := r.Form
	ph := photo.Fetch(form.Get("id"))
	if ph == nil {
		http.Redirect(w, r, "./", http.StatusGone)
		return
	}
	top, _ := strconv.ParseFloat(form.Get("top"), 32)
	bottom, _ := strconv.ParseFloat(form.Get("bottom"), 32)
	center, _ := strconv.ParseFloat(form.Get("center"), 32)
	docSize := form.Get("document_size")

	faceInfo := &pdf.FaceInfo{
		Top:    float32(top),
		Bottom: float32(bottom),
		Center: float32(center),
	}

	cnt := 0
	var reqs []*pdf.FaceRequest
	for {
		var w, h float64
		var c int64
		w, err = strconv.ParseFloat(form.Get(fmt.Sprintf("photo_size;%d;width", cnt)), 32)
		if err != nil {
			break
		}
		h, err = strconv.ParseFloat(form.Get(fmt.Sprintf("photo_size;%d;height", cnt)), 32)
		if err != nil {
			break
		}
		c, err = strconv.ParseInt(form.Get(fmt.Sprintf("photo_size;%d;count", cnt)), 10, 32)
		if err != nil {
			break
		}
		reqs = append(reqs, &pdf.FaceRequest{
			WidthInMm:  float32(w) * 10.0,
			HeightInMm: float32(h) * 10.0,
			Count:      int(c),
		})
		cnt = cnt + 1
	}

	before := time.Now()
	buff, err := pdf.CreateDoc(docSize, ph, faceInfo, reqs)
	log.Printf("Creating PDF took %d ms, %d bytes.", time.Now().Sub(before).Nanoseconds()/1000/1000, len(buff))
	if err != nil {
		type Data struct {
			Message string
		}
		dat := Data{
			Message: fmt.Sprintf("作成エラー：%s", err.Error()),
		}
		render("create-error", dat, w, r)
		return
	}
	w.Write(buff)
}
