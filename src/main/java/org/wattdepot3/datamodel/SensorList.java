/**
 * SensorList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * SensorList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class SensorList {
  private ArrayList<Sensor> sensors;
  
  /**
   * Default Constructor.
   */
  public SensorList() {
    sensors = new ArrayList<Sensor>();
  }

  /**
   * @return the groups
   */
  public ArrayList<Sensor> getSensors() {
    return sensors;
  }

  /**
   * @param groups the groups to set
   */
  public void setSensors(ArrayList<Sensor> groups) {
    this.sensors = groups;
  }

}
