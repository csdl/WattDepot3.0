/**
 * LocationsServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.Location;
import org.wattdepot3.restlet.LocationsResource;

/**
 * LocationsServerResource - ServerResource that handles the URI
 * "/wattdepot/{group_id}/locations/".
 * 
 * @author Cam Moore
 * 
 */
public class LocationsServerResource extends WattDepotServerResource implements LocationsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationsResource#retrieve()
   */
  @Override
  public ArrayList<Location> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/locations/");
    ArrayList<Location> ret = new ArrayList<Location>();
    for (Location l : depot.getLocations(groupId)) {
      ret.add(l);
    }
    return ret;
  }

}
