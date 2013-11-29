/**
 * UniqueIdException.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.exception;

/**
 * UniqueIdException thrown when an object is defined with the same unique id as
 * another object of the same type.
 * 
 * @author Cam Moore
 * 
 */
public class BadSensorUriException extends Exception {

  /** serial version UID. */
  private static final long serialVersionUID = 4574446966521917423L;

  /** Default Constructor. */
  public BadSensorUriException() {
    super();
  }

  /**
   * @param message
   *          A String message about the exception.
   * @param cause
   *          The Throwable cause of the exception.
   */
  public BadSensorUriException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   *          A String message about the exception.
   */
  public BadSensorUriException(String message) {
    super(message);
  }

  /**
   * @param cause
   *          The Throwable cause of the exception.
   */
  public BadSensorUriException(Throwable cause) {
    super(cause);
  }

}
