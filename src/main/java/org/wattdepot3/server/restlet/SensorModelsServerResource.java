/**
 * SensorModelsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorModelList;
import org.wattdepot3.restlet.SensorModelsResource;

/**
 * SensorModelsServerResource - Handles the SensorGroups HTTP API
 * ("/wattdepot/{group_id}/sensormodels/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelsServerResource extends WattDepotServerResource implements
    SensorModelsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelsResouce#retrieve()
   */
  @Override
  public SensorModelList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodels/");
    SensorModelList ret = new SensorModelList();
    for (SensorModel sm : depot.getSensorModels(groupId)) {
      ret.getModels().add(sm);
    }
    return ret;
  }
}
