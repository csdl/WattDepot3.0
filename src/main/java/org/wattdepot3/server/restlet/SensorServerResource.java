/**
 * SensorServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorResource;

/**
 * SensorServerResource - Handles the Sensor HTTP API
 * ("/wattdepot/{group_id}/sensor/",
 * "/wattdepot/{group_id}/sensor/{sensor_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorServerResource extends WattDepotServerResource implements SensorResource {
  private String sensorId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorId = getAttribute("sensor_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorResource#retrieve()
   */
  @Override
  public Sensor retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensor/{" + sensorId + "}");
    Sensor sensor = null;
    try {
      sensor = depot.getSensor(sensorId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (sensor == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "Sensor " + sensorId + " is not defined.");
    }
    return sensor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.restlet.SensorResource#store(org.wattdepot3.datamodel.Sensor
   * )
   */
  @Override
  public void store(Sensor sensor) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensor/ with " + sensor);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getSensorIds(groupId).contains(sensor.getId())) {
        try {
          depot.defineSensor(sensor.getName(), sensor.getUri(), sensor.getLocation(),
              sensor.getModel(), owner);
        }
        catch (UniqueIdException e) {
          setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
        }
        catch (MissMatchedOwnerException e) {
          setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
        }
      }
      else {
        depot.updateSensor(sensor);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensor/{" + sensorId + "}");
    try {
      depot.deleteSensor(sensorId, groupId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
