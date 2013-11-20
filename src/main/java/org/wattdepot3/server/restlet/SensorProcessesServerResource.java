/**
 * SensorProcessesServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.SensorProcessList;
import org.wattdepot3.restlet.SensorProcessesResource;

/**
 * SensorProcessesServerResource - Handles the SensorProcesses HTTP API
 * ("/wattdepot/{group_id}/sensorprocesses/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessesServerResource extends WattDepotServerResource implements
    SensorProcessesResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessesResouce#retrieve()
   */
  @Override
  public SensorProcessList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorprocesses/");
    SensorProcessList ret = new SensorProcessList();
    for (SensorProcess sp : depot.getSensorProcesses(groupId)) {
      ret.getProcesses().add(sp);
    }
    return ret;
  }
}
