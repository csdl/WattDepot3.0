/**
 * SensorResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.Sensor;

/**
 * SensorResource - HTTP Interface for data model Sensor.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorResource {

  /**
   * Defines GET /wattdepot/sensor/{sensor_id} API call.
   * 
   * @return The Sensor with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public Sensor retrieve();

  /**
   * Defines the PUT /wattdepot/sensor/ API call.
   * 
   * @param sensor
   *          The Sensor to store.
   */
  @Put
  public void store(Sensor sensor);

  /**
   * Defined the DEL /wattdepot/sensor/{sensor_id} API call. The id is sent
   * in the request.
   */
  @Delete
  public void remove();

}
