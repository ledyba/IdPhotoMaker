package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
)

var port = flag.Int("port", 8080, "")

func main() {
	flag.Parse() // Scan the arguments list
	http.Handle("/static", http.FileServer(assetFS()))

	log.Printf("Start at http://localhost:%d/", *port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", *port), nil))
}
