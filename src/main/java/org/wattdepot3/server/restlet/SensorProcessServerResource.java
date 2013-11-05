/**
 * SensorProcessServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorProcessResource;

/**
 * SensorProcessServerResource - Handles the SensorProcess HTTP API
 * (("/wattdepot/{group_id}/sensorprocess/",
 * "/wattdepot/{group_id}/sensorprocess/{sensorprocess_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessServerResource extends WattDepotServerResource implements
    SensorProcessResource {

  /** The sensorprocess_id from the request. */
  private String sensorProcessId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorProcessId = getAttribute("sensorprocess_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#retrieve()
   */
  @Override
  public SensorProcess retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorprocess/{" + sensorProcessId + "}");
    SensorProcess process = null;
    try {
      process = depot.getSensorProcess(sensorProcessId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (process == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "SensorProcess " + sensorProcessId
          + " is not defined.");
    }
    return process;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#store(org.wattdepot3
   * .datamodel.SensorProcess)
   */
  @Override
  public void store(SensorProcess sensorprocess) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensorprocess/ with " + sensorprocess);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getSensorProcessIds(groupId).contains(sensorprocess.getId())) {
        try {
          depot.defineSensorProcess(sensorprocess.getId(), sensorprocess.getSensor(),
              sensorprocess.getPollingInterval(), sensorprocess.getDepositoryId(), owner);
        }
        catch (UniqueIdException | MissMatchedOwnerException e) {
          setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
        }
      }
      else {
        depot.updateSensorProcess(sensorprocess);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensorprocess/{" + sensorProcessId + "}");
    try {
      depot.deleteSensorProcess(sensorProcessId, groupId);
    }
    catch (IdNotFoundException | MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
