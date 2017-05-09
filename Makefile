.PHONY: all get run clean bind dbind deploy

all:
	gofmt -w .
	go build -o id-photo-maker github.com/ledyba/IdPhotoMaker

get:
	go get -u "github.com/jteeuwen/go-bindata/..."
	go get -u "github.com/elazarl/go-bindata-assetfs/..."
	go get -u "github.com/jung-kurt/gofpdf/..."
	go get -u "github.com/nfnt/resize/..."
	go get -u "github.com/oliamb/cutter/..."

bind:
	PATH=$(GOPATH)/bin:$(PATH) $(GOPATH)/bin/go-bindata-assetfs -prefix=assets/ -pkg=main ./assets/...

dbind:
	PATH=$(GOPATH)/bin:$(PATH) $(GOPATH)/bin/go-bindata-assetfs -prefix=assets/ -debug=true -pkg=main ./assets/...

run: all
	$(GOPATH)/bin/IdPhotoMaker

clean:
	go clean github.com/ledyba/IdPhotoMaker/...

deploy: bind
	GOOS=linux GOARCH=amd64 go build -o id-photo-maker github.com/ledyba/IdPhotoMaker
	ssh ledyba.org mkdir -p /opt/run/IdPhotoMaker
	scp id-photo-maker id-photo-maker.conf ledyba:/opt/run/IdPhotoMaker
