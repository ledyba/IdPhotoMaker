/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

/**
 *
 * @author PSI
 */
public class PhotoSize {
/*
    public static final PhotoSize S25x30 = new PhotoSize(2.5f, 3.0f);
    public static final PhotoSize S30x24 = new PhotoSize(3.0f, 2.4f);
    public static final PhotoSize S30x25 = new PhotoSize(3.0f, 2.5f);
    public static final PhotoSize S30x30 = new PhotoSize(3.0f, 3.0f);
    public static final PhotoSize S30x40 = new PhotoSize(3.0f, 4.0f);
    public static final PhotoSize S32x36 = new PhotoSize(3.2f, 3.6f);
    public static final PhotoSize S34x24 = new PhotoSize(3.4f, 2.4f);
    public static final PhotoSize S35x25 = new PhotoSize(3.5f, 2.5f);
    public static final PhotoSize S35x30 = new PhotoSize(3.5f, 3.0f);
    public static final PhotoSize S36x24 = new PhotoSize(3.6f, 2.4f);
    public static final PhotoSize S40x30 = new PhotoSize(4.0f, 3.0f);
    public static final PhotoSize S40x40 = new PhotoSize(4.0f, 4.0f);
    public static final PhotoSize S40x50 = new PhotoSize(4.0f, 5.0f);
    public static final PhotoSize S40x60 = new PhotoSize(4.0f, 6.0f);
    public static final PhotoSize S45x35 = new PhotoSize(4.5f, 3.5f);
    public static final PhotoSize S45x40 = new PhotoSize(4.5f, 4.0f);
    public static final PhotoSize S45x45 = new PhotoSize(4.5f, 4.5f);
    public static final PhotoSize S50x35 = new PhotoSize(5.0f, 3.5f);
    public static final PhotoSize S50x40 = new PhotoSize(5.0f, 4.0f);
    public static final PhotoSize S50x45 = new PhotoSize(5.0f, 4.5f);
    public static final PhotoSize S50x50 = new PhotoSize(5.0f, 5.0f);
    public static final PhotoSize S55x45 = new PhotoSize(5.5f, 4.5f);
    public static final PhotoSize S60x40 = new PhotoSize(6.0f, 4.0f);
    public static final PhotoSize S60x45 = new PhotoSize(6.0f, 4.5f);
*/
    private double h;
    private double w;
    private double Wcm;
    private double Hcm;
    public PhotoSize(double w_cm,double h_cm){
        w = w_cm * 72f / 2.54f;
        h = h_cm * 72f / 2.54f;
        Wcm = w_cm;
        Hcm = h_cm;
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    public double getHcm() {
        return Hcm;
    }

    public double getWcm() {
        return Wcm;
    }

}
