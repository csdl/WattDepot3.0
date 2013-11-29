/**
 * SensorProcessesServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.CollectorMetaData;
import org.wattdepot3.datamodel.CollectorMetaDataList;
import org.wattdepot3.restlet.CollectorMetaDatasResource;

/**
 * SensorProcessesServerResource - Handles the SensorProcesses HTTP API
 * ("/wattdepot/{group_id}/sensorprocesses/").
 * 
 * @author Cam Moore
 * 
 */
public class CollectorMetaDatasServerResource extends WattDepotServerResource implements
    CollectorMetaDatasResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessesResouce#retrieve()
   */
  @Override
  public CollectorMetaDataList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorprocesses/");
    CollectorMetaDataList ret = new CollectorMetaDataList();
    for (CollectorMetaData sp : depot.getCollectorMetaDatas(groupId)) {
      ret.getDatas().add(sp);
    }
    return ret;
  }
}
