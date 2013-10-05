/**
 * 
 */
package org.wattdepot.server.depository;

/**
 * An exception that is thrown when a repository cannot find a measurement.
 * 
 * @author Cam Moore
 */
public class NoMeasurementException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -2987264347948327973L;

  /**
   * 
   */
  public NoMeasurementException() {
    super();
  }

  /**
   * @param message
   *          A String the message about the Exception.
   */
  public NoMeasurementException(String message) {
    super(message);
  }

  /**
   * @param cause
   *          The Throwable cause of the Exception.
   */
  public NoMeasurementException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   *          A String message about the exception.
   * @param cause
   *          The Throwable cause of the exception.
   */
  public NoMeasurementException(String message, Throwable cause) {
    super(message, cause);
  }

}
