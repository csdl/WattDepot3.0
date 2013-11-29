/**
 * LocationsServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.SensorLocation;
import org.wattdepot3.datamodel.SensorLocationList;
import org.wattdepot3.restlet.SensorLocationsResource;

/**
 * LocationsServerResource - ServerResource that handles the URI
 * "/wattdepot/{group_id}/locations/".
 * 
 * @author Cam Moore
 * 
 */
public class SensorLocationsServerResource extends WattDepotServerResource implements SensorLocationsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationsResource#retrieve()
   */
  @Override
  public SensorLocationList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/locations/");
    SensorLocationList list = new SensorLocationList();
    for (SensorLocation l : depot.getLocations(groupId)) {
      list.getLocations().add(l);
    }
    return list;
  }

}
