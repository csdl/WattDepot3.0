/**
 * LocationResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorLocation;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorLocationResource;

/**
 * LocationResource - WattDepot 3 Location Resource handles the Location HTTP
 * API ("/wattdepot/{group_id}/location/" and
 * "/wattdepot/{group_id}/location/{location_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorLocationServerResource extends WattDepotServerResource implements SensorLocationResource {

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
  public SensorLocation retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/location/{" + locationId + "}");
    SensorLocation loc = null;
    try {
      loc = depot.getLocation(locationId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (loc == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "Location " + locationId
          + " is not defined.");
    }
    return loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationResource#store(org.wattdepot3.datamodel
   * .Location)
   */
  @Override
  public void store(SensorLocation sensorLocation) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/location/ with " + sensorLocation);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getLocationIds(groupId).contains(sensorLocation.getId())) {
        try {
          depot.defineLocation(sensorLocation.getName(), sensorLocation.getLatitude(), sensorLocation.getLongitude(),
              sensorLocation.getAltitude(), sensorLocation.getDescription(), owner);
        }
        catch (UniqueIdException e) {
          setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
        }
      }
      else {
        depot.updateLocation(sensorLocation);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.LocationResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/location/{" + locationId + "}");
    try {
      depot.deleteLocation(locationId, groupId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }
}
