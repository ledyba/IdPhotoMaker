/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

/**
 *
 * @author PSI
 */
public class InvalidDocumentSizeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidDocumentSizeException</code> without detail message.
     */
    public InvalidDocumentSizeException() {
    }


    /**
     * Constructs an instance of <code>InvalidDocumentSizeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidDocumentSizeException(String msg) {
        super(msg);
    }
}
