/**
 * SensorModelResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.SensorModel;

/**
 * SensorModelResource - HTTP Interface for data model SensorModel.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorModelResource {

  /**
   * Defines GET /wattdepot/sensormodel/{sensormodel_id} API call.
   * 
   * @return The SensorModel with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public SensorModel retrieve();

  /**
   * Defines the PUT /wattdepot/sensormodel/ API call.
   * 
   * @param sensormodel
   *          The SensorModel to store.
   */
  @Put
  public void store(SensorModel sensormodel);

  /**
   * Defined the DEL /wattdepot/sensormodel/{sensormodel_id} API call. The id is
   * sent in the request.
   */
  @Delete
  public void remove();

}
