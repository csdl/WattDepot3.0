/**
 * WattDepotServerResource.java created on Oct 30, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot3.server.WattDepot;
import org.wattdepot3.server.WattDepotApplication;

/**
 * WattDepotServerResource - Base class for WattDepot ServerResources. Gets the
 * WattDepot instance from the WattDepotApplication.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotServerResource extends ServerResource {
  /** The WattDepot instance. */
  protected WattDepot depot;
  /** The groupId in the request. */
  protected String groupId;
  

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    WattDepotApplication app = (WattDepotApplication) getApplication();
    this.depot = app.getDepot();
    this.groupId = getAttribute("group_id");
  }
}
