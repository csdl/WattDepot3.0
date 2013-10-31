/**
 * SensorsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.restlet.SensorsResouce;

/**
 * SensorsServerResource - Handles the Sensors HTTP API
 * ("/wattdepot/{group_id}/sensors/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorsServerResource extends WattDepotServerResource implements SensorsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorsResouce#retrieve()
   */
  @Override
  public ArrayList<Sensor> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodels/");
    return null;
  }
}
