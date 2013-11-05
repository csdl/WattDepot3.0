/**
 * SensorGroupServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorGroupResource;

/**
 * SensorGroupServerResource - Handles the SensorGroup HTTP API
 * ("/wattdepot/{group_id}/sensorgroup/",
 * "/wattdepot/{group_id}/sensorgroup/{sensorgroup_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupServerResource extends WattDepotServerResource implements
    SensorGroupResource {

  /** The sensorgroup_id from the request. */
  private String sensorGroupId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorGroupId = getAttribute("sensorgroup_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#retrieve()
   */
  @Override
  public SensorGroup retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorgroup/{" + sensorGroupId + "}");
    SensorGroup group = null;
    try {
      group = depot.getSensorGroup(sensorGroupId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (group == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "SensorGroup " + sensorGroupId
          + " is not defined.");

    }
    return group;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#store(org.wattdepot3
   * .datamodel.SensorGroup)
   */
  @Override
  public void store(SensorGroup sensorgroup) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensorgroup/ with " + sensorgroup);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getSensorGroupIds(groupId).contains(sensorgroup.getId())) {
        try {
          depot.defineSensorGroup(sensorgroup.getId(), sensorgroup.getSensors(), owner);
        }
        catch (UniqueIdException | MissMatchedOwnerException e) {
          setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
        }
      }
      else {
        depot.updateSensorGroup(sensorgroup);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensorgroup/{" + sensorGroupId + "}");
    try {
      depot.deleteSensorGroup(sensorGroupId, groupId);
    }
    catch (IdNotFoundException | MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
