/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.DocumentException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author psi
 */
public class PhotoRequest {

    public PhotoRequest(PhotoInfo Info, PhotoSize Size, int Count) throws InvalidPhotoPositionException {
        this.Info = Info;
        this.Size = Size;
        this.Count = Count;
        calcFacePos();
    }

    private PhotoInfo Info;
    private PhotoSize Size;
    private int Count;
    private int X1;
    private int Y1;
    private int X2;
    private int Y2;
    private com.itextpdf.text.Image Mask;

    private void calcFacePos() throws InvalidPhotoPositionException{
        int center = (int)(this.Info.getCenter() * Info.getWidth());
        int top = (int)(this.Info.getTop() * Info.getHeight());
        int bottom = (int)(this.Info.getBottom() * Info.getHeight());
        if(top > bottom){
            throw new InvalidPhotoPositionException("face.pos.top.and.bottom");
        }
        int face_height = bottom-top;
        //規定はhttp://www.seikatubunka.metro.tokyo.jp/photo/index.htmlを参考に
        int photo_height = face_height * 45 / 34;
        int photo_margin_top = face_height * 4 / 34;
        int photo_width = (int)(photo_height * this.Size.getW() / this.Size.getH());
        this.X1 = center-(photo_width/2);
        this.Y1 = top-photo_margin_top;
        this.X2 = center+(photo_width/2);
        this.Y2 = this.Y1+photo_height;
        if(this.X1 < 0 || this.Y1 < 0 || this.X2 > Info.getWidth() || this.Y2 > Info.getHeight()){
            throw new InvalidPhotoPositionException("face.pos.invalid.range");
        }
        //マスクの作成
        BufferedImage mask = new BufferedImage(Info.getWidth(), Info.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = mask.getGraphics();
        g.fillRect(X1,Y1, photo_width, photo_height);
        g.dispose();
        try {
            Mask = com.itextpdf.text.Image.getInstance(mask, null, true);
            Mask.makeMask();
        } catch (DocumentException ex) {
            Logger.getLogger(PhotoRequest.class.getName()).log(Level.SEVERE, "failed to create mask.", ex);
        } catch (IOException ex) {
            Logger.getLogger(PhotoRequest.class.getName()).log(Level.SEVERE, "failed to create mask.", ex);
        }
        Mask.setInverted(true);
    }
    /**
     * Get the value of Size
     *
     * @return the value of Size
     */
    public PhotoSize getSize() {
        return Size;
    }
    /**
     * Get the value of Count
     *
     * @return the value of Count
     */
    public int getCount() {
        return Count;
    }

    public int getX1() {
        return X1;
    }

    public int getX2() {
        return X2;
    }

    public int getY1() {
        return Y1;
    }

    public int getY2() {
        return Y2;
    }

    public PhotoInfo getInfo() {
        return Info;
    }

    public com.itextpdf.text.Image getMask() {
        return Mask;
    }

}
