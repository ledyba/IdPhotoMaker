/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.geom.AffineTransform;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author psi
 */
public class LayoutManager {
    private DocumentSize DocSize;
    private PhotoRequest[] Requests;
    private ByteArrayOutputStream Baos;
    private final double MARGIN = 12.0f;
    private final double FONT_SIZE = 12.0f;

    public LayoutManager(DocumentSize DocSize, PhotoRequest[] Requests) {
        this.DocSize = DocSize;
        this.Requests = Requests;
    }

    public void doLayout() throws DocumentException, IOException{
        Document doc = new Document(this.DocSize.getPdfRectangle(),0,0,0,0);
        //OpenWriter
        Baos = new ByteArrayOutputStream(1024*1024*20);
        PdfWriter writer = PdfWriter.getInstance(doc , Baos);

        doc.addAuthor("Fairy and Rockets");
        doc.open();
        layoutPhotos(doc,writer);
        doc.close();
    }
    private void layoutPhotos(Document doc,PdfWriter writer) throws IOException, DocumentException{
        PdfContentByte cb = writer.getDirectContent();
        double draw_y = MARGIN;
        final double left_width = doc.getPageSize().getWidth()-(MARGIN*2);
        for(PhotoRequest req : this.Requests){
            /*
             * 文字の描画
             */
            draw_y += FONT_SIZE;
            cb.beginText();
            cb.setFontAndSize(BaseFont.createFont(), (float)FONT_SIZE);
            cb.moveText((float)MARGIN, (float)(doc.getPageSize().getHeight()-draw_y));
            cb.showText(Double.toString(req.getSize().getHcm())+" x "+Double.toString(req.getSize().getWcm()));
            cb.endText();
            draw_y += 2.0f;
            /*
             * 写真の描画
             */
            //毎回生成しないと、マスクがうまく働かない。
            com.itextpdf.text.Image image = req.getInfo().createPdfImage();
            image.setImageMask(req.getMask());
            final double scale_x = req.getSize().getW()/(req.getX2()-req.getX1());
            final double scale_y = req.getSize().getH()/(req.getY2()-req.getY1());
            final int photo_in_one_line = (int)(left_width / req.getSize().getW());
            final double line_margin = (left_width-(req.getSize().getW()*photo_in_one_line)) / (photo_in_one_line-1);
            for(int i=0;(photo_in_one_line * i) < req.getCount();i++){
                if(draw_y+req.getSize().getH()+MARGIN > doc.getPageSize().getHeight()){//改ページ
                    newPage(cb);
                    draw_y = MARGIN;
                }
                double draw_x = MARGIN;
                for(int j=0;j<photo_in_one_line;j++){
                    AffineTransform xform = AffineTransform.getTranslateInstance(draw_x-(req.getX1()*(scale_x)),draw_y-(req.getY1()*(scale_y)));
                    xform.scale(scale_x,scale_y);
                    xform.translate(0, req.getInfo().getHeight());
                    xform.scale(req.getInfo().getWidth(),req.getInfo().getHeight());
                    AffineTransform inverse = this.normalizeMatrix(doc.getPageSize().getHeight());
                    inverse.concatenate(xform);
                    inverse.concatenate(AffineTransform.getScaleInstance(1,-1));
                    cb.addImage(image, inverse);
                    draw_x+=req.getSize().getW();
                    draw_x+=line_margin;
                }
                draw_y += 2.0f;
                draw_y += req.getSize().getH();
            }
         }
    }
    private AffineTransform normalizeMatrix(double height) {
        double[] mx = new double[6];
        AffineTransform result = AffineTransform.getTranslateInstance(0,0);
        result.getMatrix(mx);
        mx[3]=-1;
        mx[5]=height;
        result = new AffineTransform(mx);
        return result;
    }
    private void newPage(PdfContentByte cb){
        cb.getPdfDocument().newPage();
    }
    public void outPdf(OutputStream os) throws IOException{
        Baos.writeTo(os);
    }
}
