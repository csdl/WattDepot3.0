/**
 * LocationsServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.LocationList;
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
  public LocationList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/locations/");
    LocationList list = new LocationList();
    for (Location l : depot.getLocations(groupId)) {
      list.getLocations().add(l);
    }
    return list;
  }

}
