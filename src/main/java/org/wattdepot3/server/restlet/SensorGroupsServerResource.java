/**
 * SensorGroupsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorGroupList;
import org.wattdepot3.restlet.SensorGroupsResource;

/**
 * SensorGroupsServerResource - Handles the SensorGroups HTTP API
 * ("/wattdepot/{group_id}/sensorgroups/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupsServerResource extends WattDepotServerResource implements
    SensorGroupsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupsResouce#retrieve()
   */
  @Override
  public SensorGroupList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorgroups/");
    SensorGroupList ret = new SensorGroupList();
    for (SensorGroup sg : depot.getSensorGroups(groupId)) {
      ret.getGroups().add(sg);
    }
    return ret;
  }

}
