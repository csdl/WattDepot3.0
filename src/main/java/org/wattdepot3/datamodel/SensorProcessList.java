/**
 * SensorProcessList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * SensorProcessList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class SensorProcessList {
  private ArrayList<SensorProcess> processes;
  
  /**
   * Default Constructor.
   */
  public SensorProcessList() {
    processes = new ArrayList<SensorProcess>();
  }

  /**
   * @return the models
   */
  public ArrayList<SensorProcess> getProcesses() {
    return processes;
  }

  /**
   * @param models the models to set
   */
  public void setProcesses(ArrayList<SensorProcess> models) {
    this.processes = models;
  }

}
