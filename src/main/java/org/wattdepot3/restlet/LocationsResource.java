/**
 * LocationsResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.Location;

/**
 * LocationsResource - The HTTP API for Locations.
 * 
 * @author Cam Moore
 * 
 */
public interface LocationsResource {

  /**
   * Defines the GET /wattdepot/locations/ API call.
   * 
   * @return a List of the defined Locations.
   */
  @Get("json")  // Use JSON as transport encoding.
  public ArrayList<Location> retrieve();

}
