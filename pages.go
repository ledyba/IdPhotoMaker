package main

import (
	"fmt"
	"net/http"

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

func createHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	form := r.Form
	form.Get("")
}
