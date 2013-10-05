/**
 * MeasurementTypeException.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository;

/**
 * MeasurementTypeException thrown when a WattDepository is given a measurement
 * of the wrong type.
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementTypeException extends Exception {

  /** Serial Version ID. */
  private static final long serialVersionUID = -1058410370738816067L;

  /** Default constructor. */
  public MeasurementTypeException() {
    super();
  }

  /**
   * @param message
   *          A String message about the exception.
   */
  public MeasurementTypeException(String message) {
    super(message);
  }

  /**
   * @param cause
   *          The Throwable cause of the exception.
   */
  public MeasurementTypeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   *          A String message about the exception.
   * @param cause
   *          The Throwable cause of the exception.
   */
  public MeasurementTypeException(String message, Throwable cause) {
    super(message, cause);
  }
}
