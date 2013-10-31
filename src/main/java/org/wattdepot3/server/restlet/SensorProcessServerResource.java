/**
 * SensorProcessServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.restlet.SensorProcessResource;

/**
 * SensorProcessServerResource - Handles the SensorProcess HTTP API
 * (("/wattdepot/{group_id}/sensorprocess/",
 * "/wattdepot/{group_id}/sensorprocess/{sensorprocess_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessServerResource extends WattDepotServerResource implements
    SensorProcessResource {

  /** The sensorprocess_id from the request. */
  private String sensorProcessId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.sensorProcessId = getAttribute("sensorprocess_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#retrieve()
   */
  @Override
  public SensorProcess retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorprocess/{" + sensorProcessId + "}");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#store(org.wattdepot3
   * .datamodel.SensorProcess)
   */
  @Override
  public void store(SensorProcess sensorprocess) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensorprocess/ with " + sensorprocess);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorProcessResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensorprocess/{" + sensorProcessId + "}");
  }

}
