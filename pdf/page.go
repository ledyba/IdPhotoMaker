package pdf

import (
	"bytes"
	"fmt"
	"image"
	"image/jpeg"

	"github.com/jung-kurt/gofpdf"
	"github.com/ledyba/IdPhotoMaker/photo"
)

// CreateDoc ...
func CreateDoc(docSizeName string, ph *photo.Photo, faceInfo *FaceInfo, reqs []*FaceRequest) ([]byte, error) {
	var err error
	img, _, err := image.Decode(bytes.NewReader(ph.Data))
	if err != nil {
		return nil, err
	}
	faces := make([]*Face, len(reqs))
	pdf := gofpdf.New("P", "mm", docSizeName, "")
	pdf.AddPage()
	pdf.SetFont("Arial", "B", 16)
	for i, req := range reqs {
		name := fmt.Sprintf("%d", i)
		var face *Face
		face, err = CreateFace(img, ph, faceInfo, req)
		if err != nil {
			return nil, err
		}
		faces[i] = face
		var buff bytes.Buffer
		err = jpeg.Encode(&buff, face.Image, &jpeg.Options{Quality: 95})
		if err != nil {
			return nil, err
		}
		pdf.RegisterImageOptionsReader(name, gofpdf.ImageOptions{ImageType: "jpeg"}, bytes.NewReader(buff.Bytes()))
		w, h, _ := pdf.PageSize(pdf.PageNo())
		if (h-pdf.GetY())-float64(16+face.HeightInMm) < 10 {
			pdf.AddPage()
		}
		const space = 3
		margin := pdf.GetX()
		line := int((w - 2*margin + space) / float64(face.WidthInMm+space))
		left := (w - 2*margin + space) - float64(line)*float64(face.WidthInMm+space)
		step := left/float64(line) + float64(face.WidthInMm) + space
		pdf.Writef(12, "%.1fcmx%.1fcm\n", face.HeightInMm/10.0, face.WidthInMm/10.0)
		total := 0
		for total < req.Count {
			if h-(pdf.GetY()+float64(face.HeightInMm)) < 10.0 {
				pdf.AddPage()
				pdf.SetY(10.0)
			}
			for i := 0; i < line; i++ {
				pdf.ImageOptions(name, pdf.GetX()+step*float64(i), pdf.GetY(), float64(face.WidthInMm), float64(face.HeightInMm), false, gofpdf.ImageOptions{}, 0, "")
				total = total + 1
			}
			pdf.SetY(pdf.GetY() + float64(face.HeightInMm) + space)
		}
	}
	var buff bytes.Buffer
	err = pdf.Output(&buff)
	if err != nil {
		return nil, err
	}
	return buff.Bytes(), nil
}
