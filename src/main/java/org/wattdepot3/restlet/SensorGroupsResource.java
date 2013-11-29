/**
 * SensorGroupsResouce.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.SensorGroupList;

/**
 * SensorGroupsResouce - HTTP Interface for SensorGroups.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorGroupsResource {
  /**
   * Defines the GET /wattdepot/sensorgroups/ API call.
   * 
   * @return a List of the defined SensorGroups.
   */
  @Get("json") // Use JSON as transport encoding.
  public SensorGroupList retrieve();

}
