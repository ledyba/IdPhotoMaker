/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

/**
 *
 * @author psi
 */
public class InvalidPhotoPositionException extends Exception{

    public InvalidPhotoPositionException(Throwable cause) {
        super(cause);
    }

    public InvalidPhotoPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPhotoPositionException(String message) {
        super(message);
    }

    public InvalidPhotoPositionException() {
    }
}
