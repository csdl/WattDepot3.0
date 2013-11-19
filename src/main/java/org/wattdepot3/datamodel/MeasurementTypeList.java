/**
 * MeasurementTypeList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * MeasurementTypeList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class MeasurementTypeList {
  private ArrayList<MeasurementType> types;
  
  /**
   * Default Constructor.
   */
  public MeasurementTypeList() {
    types = new ArrayList<MeasurementType>();
  }

  /**
   * @return the measurements
   */
  public ArrayList<MeasurementType> getMeasurementTypes() {
    return types;
  }

  /**
   * @param measurements the measurements to set
   */
  public void setMeasurementTypes(ArrayList<MeasurementType> measurements) {
    this.types = measurements;
  }

}
