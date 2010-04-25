/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import java.lang.reflect.Field;
import java.util.TreeMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PSI
 */
public class DocumentSize {

    public static final DocumentSize LETTER = new DocumentSize(612, 792);
    public static final DocumentSize A0 = new DocumentSize(2384, 3370);
    public static final DocumentSize A1 = new DocumentSize(1684, 2384);
    public static final DocumentSize A2 = new DocumentSize(1191, 1684);
    public static final DocumentSize A3 = new DocumentSize(842, 1191);
    public static final DocumentSize A4 = new DocumentSize(595, 842,true);
    public static final DocumentSize A5 = new DocumentSize(420, 595);
    public static final DocumentSize A6 = new DocumentSize(297, 420);
    public static final DocumentSize A7 = new DocumentSize(210, 297);
    public static final DocumentSize A8 = new DocumentSize(148, 210);
    public static final DocumentSize A9 = new DocumentSize(105, 148);
    public static final DocumentSize A10 = new DocumentSize(73, 105);
    public static final DocumentSize B0 = new DocumentSize(2834, 4008);
    public static final DocumentSize B1 = new DocumentSize(2004, 2834);
    public static final DocumentSize B2 = new DocumentSize(1417, 2004);
    public static final DocumentSize B3 = new DocumentSize(1000, 1417);
    public static final DocumentSize B4 = new DocumentSize(708, 1000);
    public static final DocumentSize B5 = new DocumentSize(498, 708);
    public static final DocumentSize B6 = new DocumentSize(354, 498);
    public static final DocumentSize B7 = new DocumentSize(249, 354);
    public static final DocumentSize B8 = new DocumentSize(175, 249);
    public static final DocumentSize B9 = new DocumentSize(124, 175);
    public static final DocumentSize B10 = new DocumentSize(87, 124);
    public static final Map<String,DocumentSize> SizeList;
    static {
        Map<String,DocumentSize> hash = new TreeMap<String,DocumentSize>();
        for(Field field : DocumentSize.class.getDeclaredFields()){
            if(field.getType().equals(DocumentSize.class)){
                try {
                    hash.put(field.getName(), (DocumentSize)field.get(null));
                } catch (NullPointerException ex) {
                    continue;
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(DocumentSize.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DocumentSize.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        SizeList = hash;
    }
    private double w, h;
    private String message_id;
    private boolean isDefault;

    public DocumentSize(double w, double h) {
        this.isDefault = false;
        this.w = w;
        this.h = h;
        this.message_id = null;
    }
    public DocumentSize(double w, double h,boolean isdefault) {
        this.isDefault = isdefault;
        this.w = w;
        this.h = h;
        this.message_id = null;
    }
    public DocumentSize(double w, double h,boolean isdefault,String message_id) {
        this.isDefault = isdefault;
        this.w = w;
        this.h = h;
        this.message_id = message_id;
    }

    /**
     * Get the value of isDefault
     *
     * @return the value of isDefault
     */
    public boolean isDefault() {
        return isDefault;
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    public String getMessageID(){
        return this.message_id;
    }

    protected Rectangle getPdfRectangle() {
        return new RectangleReadOnly((float)this.w, (float)this.h);
    }

    public static final Map<String,DocumentSize> getSizeList() {
        return SizeList;
    }

    public static DocumentSize getSizeByName(String name) throws InvalidDocumentSizeException {
        name = name.trim().toUpperCase();
        int pos = name.indexOf(' ');
        if (pos == -1) {
            try {
                Field field = DocumentSize.class.getDeclaredField(name.toUpperCase());
                return (DocumentSize) field.get(null);
            } catch (Exception e) {
                throw new InvalidDocumentSizeException("Invalid Document Size Format: "+name);
            }
        } else {
            try {
                String width = name.substring(0, pos);
                String height = name.substring(pos + 1);
                double w_pt = Double.parseDouble(width) * 72f / 2.54f;
                double h_pt = Double.parseDouble(height) * 72f / 2.54f;
                return new DocumentSize(w_pt, h_pt);
            } catch (Exception e) {
                throw new InvalidDocumentSizeException("Invalid Document Size Format: "+name);
            }
        }
    }
}
