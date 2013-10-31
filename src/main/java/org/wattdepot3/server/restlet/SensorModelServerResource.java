/**
 * SensorModelServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.restlet.SensorModelResource;

/**
 * SensorModelServerResource - Handles the SensorModel HTTP API
 * ("/wattdepot/{group_id}/sensormodel/",
 * "/wattdepot/{group_id}/sensormodel/{sensormodel_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelServerResource extends WattDepotServerResource implements
    SensorModelResource {

  /** The sensormodel_id from the request. */
  private String sensorModelId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.sensorModelId = getAttribute("sensormodel_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#retrieve()
   */
  @Override
  public SensorModel retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#store(org.wattdepot3
   * .datamodel.SensorModel)
   */
  @Override
  public void store(SensorModel sensormodel) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/sensormodel/ with " + sensormodel);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
  }

}
