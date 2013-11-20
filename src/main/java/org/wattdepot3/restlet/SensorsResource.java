/**
 * SensorsResouce.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.SensorList;

/**
 * SensorsResouce - HTTP Interface for Sensors.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorsResource {
  /**
   * Defines the GET /wattdepot/sensors/ API call.
   * 
   * @return a List of the defined Sensors.
   */
  @Get("json") // Use JSON as transport encoding.
  public SensorList retrieve();

}
