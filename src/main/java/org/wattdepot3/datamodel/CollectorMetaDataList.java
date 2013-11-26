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
public class CollectorMetaDataList {
  private ArrayList<CollectorMetaData> processes;
  
  /**
   * Default Constructor.
   */
  public CollectorMetaDataList() {
    processes = new ArrayList<CollectorMetaData>();
  }

  /**
   * @return the models
   */
  public ArrayList<CollectorMetaData> getDatas() {
    return processes;
  }

  /**
   * @param models the models to set
   */
  public void setDatas(ArrayList<CollectorMetaData> models) {
    this.processes = models;
  }

}
