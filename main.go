package main

import (
	"flag"
	"fmt"
	"html/template"
	_ "image/gif"
	_ "image/jpeg"
	"log"
	"net/http"

	"github.com/ledyba/IdPhotoMaker/photo"
)

var port = flag.Int("port", 8080, "")

func render(templateName string, dat interface{}, w http.ResponseWriter, r *http.Request) {
	funcMap := template.FuncMap{}
	templates := template.Must(
		template.New("").Funcs(funcMap).ParseFiles(
			"templates/main.html",
			fmt.Sprintf("templates/%s.html", templateName)))
	err := templates.ExecuteTemplate(w, "base", dat)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

func main() {
	flag.Parse() // Scan the arguments list
	photo.InitCache()
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(assetFS())))
	http.HandleFunc("/", mainHandler)
	http.HandleFunc("/upload", uploadHandler)
	http.HandleFunc("/thumb", thumbHandler)
	http.HandleFunc("/pdf", thumbHandler)

	log.Printf("Start at http://localhost:%d/", *port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", *port), nil))
}
