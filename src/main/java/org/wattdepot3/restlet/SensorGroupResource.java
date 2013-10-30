/**
 * SensorGroupResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.SensorGroup;

/**
 * SensorGroupResource - HTTP Interface for data model SensorGroup.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorGroupResource {

  /**
   * Defines GET /wattdepot/sensorgroup/{sensorgroup_id} API call.
   * 
   * @return The SensorGroup with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public SensorGroup retrieve();

  /**
   * Defines the PUT /wattdepot/sensorgroup/ API call.
   * 
   * @param sensorgroup
   *          The SensorGroup to store.
   */
  @Put
  public void store(SensorGroup sensorgroup);

  /**
   * Defined the DEL /wattdepot/sensorgroup/{sensorgroup_id} API call. The id is
   * sent in the request.
   */
  @Delete
  public void remove();

}
