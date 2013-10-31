/**
 * LocationResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.LocationResource;

/**
 * LocationResource - WattDepot 3 Location Resource handles the Location HTTP
 * API ("/wattdepot/{group_id}/location/" and
 * "/wattdepot/{group_id}/location/{location_id}").
 * 
 * @author Cam Moore
 * 
 */
public class LocationServerResource extends WattDepotServerResource implements LocationResource {

  private String locationId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.locationId = getAttribute("location_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationResource#retrieve()
   */
  @Override
  public Location retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/location/{" + locationId + "}");
    return new Location(locationId, new Double(21.294642), new Double(-157.812727), new Double(30),
        "Hale Aloha Ilima residence hall 6th floor", UserGroup.ADMIN_GROUP);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationResource#store(org.wattdepot3.datamodel
   * .Location)
   */
  @Override
  public void store(Location location) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/location/ with " + location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/location/{" + locationId + "}");
  }

}
