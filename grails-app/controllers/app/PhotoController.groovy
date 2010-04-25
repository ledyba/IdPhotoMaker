package app

class PhotoController {

    static allowedMethods = [save: "POST"]

    def image = {
        Photo photoInstance = Photo.get(params.id)
        if(photoInstance.passId == params.passid){
            response.contentType = photoInstance.mimeType;
            response.outputStream << photoInstance.data;
        }else{
            redirect(uri:"/")
        }
    }
    def thumb = {
        Photo photoInstance = Photo.get(params.id)
        if(photoInstance.passId == params.passid){
            response.contentType = photoInstance.thumbMimeType;
            response.outputStream << photoInstance.thumb;
        }else{
            redirect(uri:"/")
        }
    }
    def save = {
        def photoInstance = new Photo(params)
        photoInstance.mimeType = request.getFile("data").contentType;
        //FIXME: ま　た　I　E　の　糞　仕　様
        if(photoInstance.mimeType == "image/pjpeg"){
            photoInstance.mimeType = "image/jpeg";
        }
        photoInstance.thumbMimeType = "image/jpeg";
        if(photoInstance.validateData() && photoInstance.validateMimeType()){
            //コンバート
            try{
                photoInstance.makeThumb("jpg");
            }catch(Exception e){
            }
        }
        photoInstance.lastUpdated = new Date();
        if (photoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'photo.label', default: 'Photo'), photoInstance.id])}"
            chain(controller:"pdf" ,action:"index", params:[id:"${photoInstance.id}", passid:"${photoInstance.passId}"], model:[photoInstance: photoInstance]);
        } else {
            render(view: "create", model: [photoInstance: photoInstance])
        }
    }
}
