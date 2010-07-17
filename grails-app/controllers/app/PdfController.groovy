package app
import pdf.*;

class PdfController {
    static allowedMethods = [create: "POST"]
    def index = {
        Photo photoInstance = Photo.get(params.id)
        if(photoInstance != null && photoInstance.passId == params.passid){
            render(view: "index", model: [photoInstance: photoInstance])
        }else{
            redirect(uri:"/")
        }
    }
    def create = {
        Photo photoInstance = Photo.get(params.photo_id);
        if(photoInstance == null || photoInstance.passId != params.photo_passid){
            //パスワードが違う
            redirect(uri:"/")
            return;
        }
        def error_lists = [];
        def hash = convertParamsToHash(params);
        DocumentSize doc_size;
        //写真についてのデータ
        PhotoInfo info;
        try{
            double photo_center = Double.parseDouble(hash.photo_center);
            double photo_top = Double.parseDouble(hash.photo_top);
            double photo_bottom = Double.parseDouble(hash.photo_bottom);
            if(photo_center < 0.0f || photo_center > 1.0f ||
                photo_top < 0.0f || photo_top > 1.0f ||
                photo_bottom < 0.0f || photo_bottom > 1.0f ){
                error_lists.add("JavaScriptError: invalid range.");
            }
            info = new PhotoInfo(photoInstance,photo_center,photo_top,photo_bottom);
        }catch(NumberFormatException e){
            error_lists.add("JavaScriptError: Not a Number.");
        }
        //印刷用紙サイズ
        try{
            doc_size = DocumentSize.getSizeByName(hash.document_size);
        }catch(InvalidDocumentSizeException ex){
            error_lists.add(message(code: "pdf.create.invalid.document.size",args:[hash.document_size]));
        }
        //必要な写真のリスト
        def photo_req_list = [];
        hash.photo_size.each(){key,pair->
            double width,height;
            int count;
            //未入力SKIP
            if((pair.width == null || pair.width.length() <= 0 )&&
                ( pair.height == null || pair.height.length() <= 0 )&&
                ( pair.count == null || pair.count.length() <= 0)){
                return;//これでOK？
            }
            try{
                width = Double.parseDouble(pair.width);
                if(width <= 0){
                    error_lists.add(message(code: "pdf.create.minus.width", args:[pair.width]));
                }
            }catch(NumberFormatException e){
                error_lists.add(message(code: "pdf.create.invalid.width",args:[pair.width]));
                width = 0;
            }
            try{
                height = Double.parseDouble(pair.height);
                if(height <= 0){
                    error_lists.add(message(code: "pdf.create.minus.height",args:[pair.height]));
                }
            }catch(NumberFormatException e){
                error_lists.add(message(code: "pdf.create.invalid.height",args:[pair.count]));
                height = 0;
            }
            try{
                count = Integer.parseInt(pair.count);
                if(count < 0){
                    error_lists.add(message(code: "pdf.create.minus.count",args:[pair.count]));
                }
            }catch(NumberFormatException e){
                error_lists.add(message(code: "pdf.create.invalid.count",args:[pair.count]));
                count = 0;
            }
            PhotoRequest req;
            try{
                req = new PhotoRequest(info,new PhotoSize(width,height),count);
            }catch(InvalidPhotoPositionException ex){
                error_lists.add(message(code: ex.getMessage()));
            }
            photo_req_list.add(req);
        }
        if(photo_req_list.size <= 0){
            error_lists.add(message(code: "pdf.create.non.photos"));
        }
        PhotoRequest[] photo_request_array = photo_req_list.toArray();
        //レイアウト
        LayoutManager manager = new LayoutManager(doc_size,photo_request_array);
        //以上でPDFを作成
        if(error_lists.size <= 0){//エラーが発生してない場合だけ作る
            try{
                manager.doLayout();
            } catch(Exception ex){
                error_lists.add("[PDF Layouyt Error]"+ex.getMessage());
            }
        }
        if(error_lists.size > 0){//以上まででエラーが発生してる場合
            //パスワードが違う
            render(view: "create", model: [photoInstance: photoInstance,errorMessages:error_lists])
        }else{//発生していない場合
            photoInstance.lastUpdated = new Date();
            photoInstance.save(flush: true,validate: false);
            response.setContentType("application/pdf");
            response.setHeader("Pragma","no-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Expires","Thu, 01 Dec 1994 16:00:00 GMT");
            response.outputStream.flush();
            manager.outPdf(response.outputStream);
            response.outputStream.flush();
        }
    }
    def convertParamsToHash(def params){
        def hash = new TreeMap();
        params.each(){key,pair->
            extractHashString(hash,key,pair);
        }
        return hash;
    }
    def extractHashString(def hash,def key,def pair){
        int idx = key.indexOf(";");
        if(idx < 0){
            hash[key] = pair;
        }else{
            String hash_key = key.substring(0,idx);
            if(hash[hash_key] == null){
                hash[hash_key] = new TreeMap();
            }
            extractHashString(hash[hash_key],key.substring(idx+1),pair);
        }
    }
}
