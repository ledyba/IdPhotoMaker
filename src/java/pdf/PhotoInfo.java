/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import app.Photo;
import com.itextpdf.text.BadElementException;
import java.io.IOException;

/**
 *
 * @author psi
 */
public class PhotoInfo {
    private Photo PhotoInstance;
    private double Center;
    private double Top;
    private double Bottom;
    private int Width;
    private int Height;

    public PhotoInfo(Photo PhotoInstance, double Center, double Top, double Bottom) {
        this.PhotoInstance = PhotoInstance;
        this.Center = Center;
        this.Top = Top;
        this.Bottom = Bottom;
        Width = PhotoInstance.getWidth();
        Height = PhotoInstance.getHeight();
    }

    public double getBottom() {
        return Bottom;
    }

    public double getCenter() {
        return Center;
    }

    public int getWidth(){
        return Width;
    }

    public int getHeight(){
        return Height;
    }

    public com.itextpdf.text.Image createPdfImage() throws BadElementException, IOException {
        return com.itextpdf.text.Image.getInstance(PhotoInstance.getData());
    }

    public double getTop() {
        return Top;
    }

}
