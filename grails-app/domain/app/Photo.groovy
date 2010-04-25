package app
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import javax.imageio.ImageReadParam
import javax.imageio.ImageReadParam

class Photo {
    private static final int FIVE_MEG_IN_BYTES = 1024*1024*5;
    /**
     *写真データ
     */
    byte[] data;
    int width;
    int height;
    /**
     *upload時のMimeTypeを表示する。
     */
    String mimeType;
    //サムネイル
    byte[] thumb;
    int thumbWidth;
    int thumbHeight;
    String thumbMimeType="image/invalid";
    /**
     *これを指定することで、他人の写真を見れないようにする
     */
    String passId = makeRandomStr(32);
    /**
     *ファイル管理用に使用する
     */
    Date dateCreated;
    Date lastUpdated;
    static constraints = {
        passId(nullable: false, size:32..32, blank:false, unique:true);
        data(nullable: false, blank:false, minSize: 1,maxSize: FIVE_MEG_IN_BYTES );
        //こうして何度もチェックしないと、不必要なエラーメッセージが表示されてしまう。
        mimeType(nullable: true,validator: {val, obj->
                if(obj.validateData()){
                    if(val == null || val.isEmpty()){
                        return ["is.null"]
                    }else if(!val.startsWith("image/")){
                        return ["is.not.image"]
                    }
                }
            });
        thumb(nullable: true,validator: {val, obj->
                if(obj.validateData() && obj.validateMimeType()){
                    if(val == null || val.length <= 0){
                        return ["is.not.valid.image"]
                    }
                }
            }
        );
    }
    public boolean validateData(){
        return data != null && data.length > 0 && data.length <= FIVE_MEG_IN_BYTES;
    }
    public boolean validateMimeType(){
        return mimeType != null && mimeType.startsWith("image/")
    }
    /**
     *ランダムな文字列を生成するためのSEED
     */
    private static final String RANDOM_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     *ランダムな文字列を生成する
     */
    private String makeRandomStr(int length) {
        StringBuffer sb = new StringBuffer(length);
        Random rnd = new Random();
        for(int i=0;i<length;i++){
            sb.append(RANDOM_CHAR.charAt(rnd.nextInt(RANDOM_CHAR.length())));
        }
        return sb.toString();
    }
    /**
    * サムネイルのバイト列を作る
    */
    private void makeThumb(String format){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageReader> ireaders = ImageIO.getImageReadersByMIMEType(this.mimeType);
        while(ireaders.hasNext()){
            ImageReader ir = ireaders.next();
            ImageInputStream iis = null;
            try {
                iis = ImageIO.createImageInputStream(new ByteArrayInputStream(this.data));
                ir.setInput(iis, true, true);

                float magnification = Math.min(400.0f / ir.getWidth(0), 400.0f / ir.getHeight(0));
                this.width = ir.getWidth(0);
                this.height = ir.getHeight(0);
                int thumb_width = ir.getWidth(0) * magnification
                int thumb_height = ir.getHeight(0) * magnification;

                ImageReadParam p = ir.getDefaultReadParam();
                int skip = Math.max((int)(ir.getWidth(0)/thumb_width),(int)(ir.getHeight(0)/thumb_height));
                if(skip > 0){
                    p.setSourceSubsampling(skip, skip, (int)(skip / 2), (int)(skip / 2));
                    BufferedImage resized = ir.read(0, p);
                    if(ImageIO.write(resized,format,baos) && baos.size() > 0){
                        this.thumbWidth = resized.getWidth();
                        this.thumbHeight = resized.getHeight();
                        this.thumb = baos.toByteArray();
                        break;
                    }else{
                        this.thumb = null;
                        throw Exception("failed to convert");
                    }
                }else{
                    //メモリの無駄？まあ良いか…。
                    this.thumbWidth = this.width;
                    this.thumbHeight = this.height;
                    this.thumb = this.data;
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                if( iis != null ){
                    try{ iis.close(); }catch(IOException e){}
                }
                if( ir != null ){
                    ir.dispose();
                }
            }
        }
    }
}
