/**
 * LocationsServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.restlet.LocationsResource;

/**
 * LocationsServerResource
 *
 * @author Cam Moore
 *
 */
public class LocationsServerResource extends ServerResource implements LocationsResource {

  /* (non-Javadoc)
   * @see org.wattdepot.core.restlet.LocationsResource#retrieve()
   */
  @Override
  public ArrayList<Location> retrieve() {
    System.out.println("GET /wattdepot/locations/");
    ArrayList<Location> ret = new ArrayList<Location>();
    ret.add(new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor"));
    return ret;
  }

}
