class UrlMappings {
	static mappings = {
	  "/photo/thumb/$id/$passid"{
		controller = "photo"
		action = "thumb"
	  }
	  "/photo/image/$id/$passid"{
		controller = "photo"
		action = "image"
	  }
      "/upload"{
		controller = "photo"
		action = "save"
      }
      "/pdf/$id/$passid"{
		controller = "pdf"
		action = "index"
      }
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(view:"/index")
	  "500"(view:'/error')
	}
}
