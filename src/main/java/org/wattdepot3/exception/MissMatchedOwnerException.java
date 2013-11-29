/**
 * UniqueIdException.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.exception;

/**
 * MissMatchedOwnerException thrown when an object is defined with a different
 * owner than the owner of its components.
 * 
 * @author Cam Moore
 * 
 */
public class MissMatchedOwnerException extends Exception {

  /** Serial Version ID. */
  private static final long serialVersionUID = -7949771009371995775L;

  /** Default Constructor. */
  public MissMatchedOwnerException() {
    super();
  }

  /**
   * @param message
   *          A String message about the exception.
   * @param cause
   *          The Throwable cause of the exception.
   */
  public MissMatchedOwnerException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   *          A String message about the exception.
   */
  public MissMatchedOwnerException(String message) {
    super(message);
  }

  /**
   * @param cause
   *          The Throwable cause of the exception.
   */
  public MissMatchedOwnerException(Throwable cause) {
    super(cause);
  }

}
