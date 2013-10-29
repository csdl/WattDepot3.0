/**
 * LocationResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.MediaType;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.restlet.LocationResource;

/**
 * LocationResource - WattDepot 3 Location Resource handles the Location HTTP
 * API.
 * 
 * @author Cam Moore
 * 
 */
public class LocationServerResource extends ServerResource implements LocationResource {

  private String locationId;

  /* (non-Javadoc)
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    for (Variant v : getVariants()) {
      System.out.println(v);
    }
    // first try GET/POST with data
    this.locationId = getQuery().getValues("location_id");
    if (locationId == null) {
      // Then part of the URL
      this.locationId = getAttribute("location_id");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.LocationResource#retrieve()
   */
  @Override
  public Location retrieve() {
    System.out.println("GET /wattdepot/location/{" + locationId + "}");
    return new Location(locationId, new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot.core.restlet.LocationResource#store(org.wattdepot.core.datamodel
   * .Location)
   */
  @Override
  public void store(Location location) {
    for (Variant v : getVariants()) {
      System.out.println(v);
    }
    System.out.println("PUT /wattdepot/location/ with " + location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.LocationResource#remove()
   */
  @Override
  public void remove() {
    for (Variant v : getVariants()) {
      System.out.println(v);
    }
    System.out.println("DEL /wattdepot/location/{" + locationId + "}");
  }

}
