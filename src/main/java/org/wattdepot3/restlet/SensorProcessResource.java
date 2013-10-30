/**
 * SensorProcessResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.SensorProcess;

/**
 * SensorProcessResource - HTTP Interface for data process SensorProcess.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorProcessResource {

  /**
   * Defines GET /wattdepot/sensorprocess/{sensorprocess_id} API call.
   * 
   * @return The SensorProcess with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public SensorProcess retrieve();

  /**
   * Defines the PUT /wattdepot/sensorprocess/ API call.
   * 
   * @param sensorprocess
   *          The SensorProcess to store.
   */
  @Put
  public void store(SensorProcess sensorprocess);

  /**
   * Defined the DEL /wattdepot/sensorprocess/{sensorprocess_id} API call. The
   * id is sent in the request.
   */
  @Delete
  public void remove();

}
