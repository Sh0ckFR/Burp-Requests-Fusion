package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

func homeLink(w http.ResponseWriter, r *http.Request) {
	headers := `{"FIRST_HTTP_HEADER": "foo", "SECOND_HTTP_HEADER": "bar"}`
	fmt.Fprintf(w, string(headers))
}

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/api/", homeLink)
	log.Fatal(http.ListenAndServe(":8888", router))
}