/**
 * LocationList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * LocationList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class SensorLocationList {
  private ArrayList<SensorLocation> sensorLocations;
  
  /**
   * Default Constructor.
   */
  public SensorLocationList() {
    sensorLocations = new ArrayList<SensorLocation>();
  }

  /**
   * @return the locations.
   */
  public ArrayList<SensorLocation> getLocations() {
    return sensorLocations;
  }

  /**
   * @param sensorLocations the locations to set
   */
  public void setLocations(ArrayList<SensorLocation> sensorLocations) {
    this.sensorLocations = sensorLocations;
  }

}
