/**
 * SensorsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorList;
import org.wattdepot3.restlet.SensorsResource;

/**
 * SensorsServerResource - Handles the Sensors HTTP API
 * ("/wattdepot/{group_id}/sensors/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorsServerResource extends WattDepotServerResource implements SensorsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorsResouce#retrieve()
   */
  @Override
  public SensorList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodels/");
    SensorList ret = new SensorList();
    for (Sensor s : depot.getSensors(groupId)) {
      ret.getSensors().add(s);
    }
    return ret;
  }
}
