/**
 * SensorGroupServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.restlet.SensorGroupResource;

/**
 * SensorGroupServerResource - Handles the SensorGroup HTTP API
 * ("/wattdepot/{group_id}/sensorgroup/",
 * "/wattdepot/{group_id}/sensorgroup/{sensorgroup_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupServerResource extends WattDepotServerResource implements
    SensorGroupResource {

  /** The sensorgroup_id from the request. */
  private String sensorGroupId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.sensorGroupId = getAttribute("sensorgroup_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#retrieve()
   */
  @Override
  public SensorGroup retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensorgroup/{" + sensorGroupId + "}");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#store(org.wattdepot3
   * .datamodel.SensorGroup)
   */
  @Override
  public void store(SensorGroup sensorgroup) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensorgroup/ with " + sensorgroup);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorGroupResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensorgroup/{" + sensorGroupId + "}");
  }

}
