/**
 * IMeasurement.java created on Sep 29, 2013 by Cam Moore.
 */
package org.wattdepot.datamodel;

import java.util.Date;

/**
 * IMeasurement - interface for all measurements.
 *
 * @author Cam Moore
 *
 */
public interface IMeasurement {

  /**
   * @return The Meter making the measurement.
   */
  public Meter getMeter();
  /**
   * @return The time the measurement was made.
   */
  public Date getTimestamp();
  
  /**
   * @return The value of the Measurement.
   */
  public Double getValue();
  
}
