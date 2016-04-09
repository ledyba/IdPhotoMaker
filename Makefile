.PHONY: all get run clean bind dbind deploy

all:
	gofmt -w .
	go install github.com/ledyba/IdPhotoMaker/...

get:
	go get -u "github.com/jteeuwen/go-bindata/..."
	go get -u "github.com/elazarl/go-bindata-assetfs/..."
	go get -u "github.com/jung-kurt/gofpdf/..."
	go get -u "github.com/nfnt/resize/..."

bind:
	PATH=$(GOPATH)/bin:$(PATH) $(GOPATH)/bin/go-bindata-assetfs -pkg=main ./static/...

dbind:
	PATH=$(GOPATH)/bin:$(PATH) $(GOPATH)/bin/go-bindata-assetfs -debug=true -pkg=main ./static/...

run: all
	$(GOPATH)/bin/IdPhotoMaker

clean:
	go clean github.com/ledyba/IdPhotoMaker/...

deploy: bind
	GOOS=linux GOARCH=amd64 go build -o id-photo-maker github.com/ledyba/IdPhotoMaker/...
	ssh ledyba.org mkdir -p /opt/run/IdPhotoMaker
	scp id-photo-maker id-photo-maker.conf ledyba:/opt/run/IdPhotoMaker
