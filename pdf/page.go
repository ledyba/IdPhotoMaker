package pdf

import (
	"github.com/ledyba/IdPhotoMaker/photo"
	"github.com/signintech/gopdf"
)

// DocumentSize ...
type DocumentSize struct {
	Width  int
	Height int
}

// DocumentSizes ...
var DocumentSizes = map[string]DocumentSize{
	"LETTER": DocumentSize{612, 792},
	"A0":     DocumentSize{2384, 3370},
	"A1":     DocumentSize{1684, 2384},
	"A2":     DocumentSize{1191, 1684},
	"A3":     DocumentSize{841, 1191},
	"A4":     DocumentSize{595, 842},
	"A5":     DocumentSize{420, 595},
	"A6":     DocumentSize{297, 420},
	"A7":     DocumentSize{210, 297},
	"A8":     DocumentSize{148, 210},
	"A9":     DocumentSize{105, 148},
	"A10":    DocumentSize{73, 105},
	"B0":     DocumentSize{2834, 4008},
	"B1":     DocumentSize{2004, 2834},
	"B2":     DocumentSize{1417, 2004},
	"B3":     DocumentSize{1000, 1417},
	"B4":     DocumentSize{708, 1000},
	"B5":     DocumentSize{498, 708},
	"B6":     DocumentSize{354, 498},
	"B7":     DocumentSize{249, 354},
	"B8":     DocumentSize{175, 249},
	"B9":     DocumentSize{124, 175},
	"B10":    DocumentSize{87, 124},
}

// CreateDoc ...
func CreateDoc(docSize string, ph *photo.Photo, faceInfo *FaceInfo, reqs []*FaceRequest) {
	pdf := gopdf.GoPdf{}
	pdf.Start(gopdf.Config{Unit: "pt", PageSize: gopdf.Rect{W: 595.28, H: 841.89}}) //595.28, 841.89 = A4
	pdf.AddPage()
}
