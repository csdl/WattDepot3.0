/**
 * SensorModelList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * SensorModelList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class SensorModelList {
  private ArrayList<SensorModel> models;
  
  /**
   * Default Constructor.
   */
  public SensorModelList() {
    models = new ArrayList<SensorModel>();
  }

  /**
   * @return the models
   */
  public ArrayList<SensorModel> getModels() {
    return models;
  }

  /**
   * @param models the models to set
   */
  public void setModels(ArrayList<SensorModel> models) {
    this.models = models;
  }

}
