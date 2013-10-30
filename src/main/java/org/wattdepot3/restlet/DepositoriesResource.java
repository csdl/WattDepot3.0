/**
 * LocationsResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.Depository;

/**
 * LocationsResource - The HTTP API for Locations.
 * 
 * @author Cam Moore
 * 
 */
public interface DepositoriesResource {

  /**
   * Defines the GET /wattdepot/depositories/ API call.
   * 
   * @return a List of the defined Locations.
   */
  @Get("json")  // Return data as JSON
  public ArrayList<Depository> retrieve();

}
