package pdf

import (
	"errors"
	"image"

	"github.com/ledyba/IdPhotoMaker/photo"
	"github.com/oliamb/cutter"
)

// Face represents cropped image
type Face struct {
	Image      image.Image
	WidthInMm  float32
	HeightInMm float32
	Width      int
	Height     int
}

// FaceRequest ...
type FaceRequest struct {
	WidthInMm  float32
	HeightInMm float32
	Count      int
}

// FaceInfo ...
type FaceInfo struct {
	Center float32
	Top    float32
	Bottom float32
}

// ErrUpsideDownFace ...
var ErrUpsideDownFace = errors.New("あごの下のラインが頭のてっぺんのラインより上にありますよ。")

// ErrInvalidRange ..
var ErrInvalidRange = errors.New("顔の周りの余白が狭すぎます。あなたの顔がもっと内側に写っている写真を使ってください。")

// CreateFace .
func CreateFace(img image.Image, ph *photo.Photo, info *FaceInfo, req *FaceRequest) (*Face, error) {
	center := info.Center * float32(ph.Width)
	top := info.Top * float32(ph.Height)
	bottom := info.Bottom * float32(ph.Height)
	if top > bottom {
		return nil, ErrUpsideDownFace
	}
	faceHeight := bottom - top
	//規定はhttp://www.seikatubunka.metro.tokyo.jp/photo/index.htmlを参考に
	photoHeight := faceHeight * 45 / 34
	photoMarginTop := faceHeight * 4 / 34
	photoWidth := photoHeight * req.WidthInMm / req.HeightInMm
	x0 := center - (photoWidth / 2)
	y0 := top - photoMarginTop
	x1 := center + (photoWidth / 2)
	y1 := y0 + photoHeight
	if x0 < 0 || y0 < 0 || x1 > float32(ph.Width) || y1 > float32(ph.Height) {
		return nil, ErrInvalidRange
	}
	croppedImg, err := cutter.Crop(img, cutter.Config{
		Width:  int(x1 - x0),
		Height: int(y1 - y0),
		Anchor: image.Point{int(x0), int(y0)},
		Mode:   cutter.TopLeft,
	})
	if err != nil {
		return nil, err
	}

	face := &Face{
		Image:      croppedImg,
		HeightInMm: req.HeightInMm,
		WidthInMm:  req.WidthInMm,
		Width:      int(photoWidth),
		Height:     int(photoHeight),
	}
	return face, nil
}
