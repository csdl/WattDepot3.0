/**
 * SensorServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Sensor;
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
    return null;
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
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensor/{" + sensorId + "}");

  }

}
