/**
 * SensorProcessesResouce.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.SensorProcess;

/**
 * SensorProcessesResouce - HTTP Interface for SensorProcesses.
 * 
 * @author Cam Moore
 * 
 */
public interface SensorProcessesResouce {
  /**
   * Defines the GET /wattdepot/sensorprocesses/ API call.
   * 
   * @return a List of the defined SensorProcesses.
   */
  @Get("json") // Use JSON as transport encoding.
  public ArrayList<SensorProcess> retrieve();

}
