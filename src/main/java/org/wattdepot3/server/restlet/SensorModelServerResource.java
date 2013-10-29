/**
 * SensorModelServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorModelResource;

/**
 * SensorModelServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelServerResource extends ServerResource implements SensorModelResource {

  /** The sensormodel_id from the request. */
  private String sensorModelId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    // first try GET/POST with data
    this.sensorModelId = getQuery().getValues("sensormodel_id");
    if (sensorModelId == null) {
      // Then part of the URL
      this.sensorModelId = getAttribute("sensormodel_id");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorModelResource#retrieve()
   */
  @Override
  public SensorModel retrieve() {
    System.out.println("GET /wattdepot/sensormodel/{" + sensorModelId + "}");
    SensorModel model = new SensorModel(sensorModelId, "protocol1", "type1", "version1");
    return model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot.core.restlet.SensorModelResource#store(org.wattdepot.core
   * .datamodel.SensorModel)
   */
  @Override
  public void store(SensorModel sensormodel) {
    System.out.println("PUT /wattdepot/sensormodel/ with " + sensormodel);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorModelResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/sensormodel/{" + sensorModelId + "}");
  }

}
