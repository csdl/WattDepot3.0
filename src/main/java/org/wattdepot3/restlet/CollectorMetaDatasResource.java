/**
 * SensorProcessesResouce.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.CollectorMetaDataList;

/**
 * SensorProcessesResouce - HTTP Interface for SensorProcesses.
 * 
 * @author Cam Moore
 * 
 */
public interface CollectorMetaDatasResource {
  /**
   * Defines the GET /wattdepot/sensorprocesses/ API call.
   * 
   * @return a List of the defined SensorProcesses.
   */
  @Get("json") // Use JSON as transport encoding.
  public CollectorMetaDataList retrieve();

}
