/**
 * SensorModelsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.restlet.SensorModelsResouce;

/**
 * SensorModelsServerResource - Handles the SensorGroups HTTP API
 * ("/wattdepot/{group_id}/sensormodels/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelsServerResource extends WattDepotServerResource implements
    SensorModelsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelsResouce#retrieve()
   */
  @Override
  public ArrayList<SensorModel> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodels/");
    return null;
  }
}
