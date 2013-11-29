/**
 * LocationsResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.DepositoryList;

/**
 * LocationsResource - The HTTP API for Locations.
 * 
 * @author Cam Moore
 * 
 */
public interface DepositoriesResource {

  /**
   * Defines the GET /wattdepot/{group_id}/depositories/ API call.
   * 
   * @return a List of the defined Locations.
   */
  @Get
  public DepositoryList retrieve();

}
