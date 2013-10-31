/**
 * SensorGroupsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.restlet.SensorGroupsResouce;

/**
 * SensorGroupsServerResource - Handles the SensorGroups HTTP API
 * ("/wattdepot/{group_id}/sensorgroups/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupsServerResource extends WattDepotServerResource implements
    SensorGroupsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupsResouce#retrieve()
   */
  @Override
  public ArrayList<SensorGroup> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorgroups/");
    return null;
  }

}
