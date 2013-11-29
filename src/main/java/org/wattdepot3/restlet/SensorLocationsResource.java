/**
 * LocationsResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.SensorLocationList;

/**
 * LocationsResource - The HTTP API for Locations.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorLocationsResource {

  /**
   * Defines the GET /wattdepot/{group_id}/locations/ API call.
   * 
   * @return a List of the defined Locations.
   */
  @Get("json")  // Use JSON as transport encoding.
  public SensorLocationList retrieve();

}
