/**
 * SensorProcessesServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.restlet.SensorProcessesResouce;

/**
 * SensorProcessesServerResource - Handles the SensorProcesses HTTP API
 * ("/wattdepot/{group_id}/sensorprocesses/").
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessesServerResource extends WattDepotServerResource implements
    SensorProcessesResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessesResouce#retrieve()
   */
  @Override
  public ArrayList<SensorProcess> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorprocesses/");
    ArrayList<SensorProcess> ret = new ArrayList<SensorProcess>();
    for (SensorProcess sp : depot.getSensorProcesses(groupId)) {
      ret.add(sp);
    }
    return ret;
  }
}
