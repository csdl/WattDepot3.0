/**
 * MeasurementList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * MeasurementList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class MeasurementList {
  private ArrayList<Measurement> measurements;
  
  /**
   * Default Constructor.
   */
  public MeasurementList() {
    measurements = new ArrayList<Measurement>();
  }

  /**
   * @return the measurements
   */
  public ArrayList<Measurement> getMeasurements() {
    return measurements;
  }

  /**
   * @param measurements the measurements to set
   */
  public void setMeasurements(ArrayList<Measurement> measurements) {
    this.measurements = measurements;
  }

}
