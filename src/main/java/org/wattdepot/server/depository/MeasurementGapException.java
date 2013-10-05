/**
 * 
 */
package org.wattdepot.server.depository;

/**
 * An exception that is thrown when a repository the gap between two measurements is too large.
 * 
 * @author Cam Moore
 */
public class MeasurementGapException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public MeasurementGapException() {

  }

  /**
   * @param message A String the message about the Exception.
   */
  public MeasurementGapException(String message) {
    super(message);
  }

  /**
   * @param cause The Throwable cause of the Exception.
   */
  public MeasurementGapException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message A String message about the exception.
   * @param cause The Throwable cause of the exception.
   */
  public MeasurementGapException(String message, Throwable cause) {
    super(message, cause);
  }

}
