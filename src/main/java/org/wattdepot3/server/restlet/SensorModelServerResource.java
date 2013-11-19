/**
 * SensorModelServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorModelResource;

/**
 * SensorModelServerResource - Handles the SensorModel HTTP API
 * ("/wattdepot/{group_id}/sensormodel/",
 * "/wattdepot/{group_id}/sensormodel/{sensormodel_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelServerResource extends WattDepotServerResource implements
    SensorModelResource {

  /** The sensormodel_id from the request. */
  private String sensorModelId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorModelId = getAttribute("sensormodel_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#retrieve()
   */
  @Override
  public SensorModel retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
    SensorModel model = null;
    try {
      model = depot.getSensorModel(sensorModelId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (model == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "SensorModel " + sensorModelId
          + " is not defined.");
    }
    return model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#store(org.wattdepot3
   * .datamodel.SensorModel)
   */
  @Override
  public void store(SensorModel sensormodel) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensormodel/ with " + sensormodel);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getSensorModelIds(groupId).contains(sensormodel.getId())) {
        try {
          depot.defineSensorModel(sensormodel.getId(), sensormodel.getProtocol(),
              sensormodel.getType(), sensormodel.getVersion(), owner);
        }
        catch (UniqueIdException e) {
          setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
        }
      }
      else {
        if (sensormodel.getOwner() == null) {
          sensormodel.setOwner(owner);
        }
        depot.updateSensorModel(sensormodel);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
    try {
      depot.deleteSensorModel(sensorModelId, groupId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
