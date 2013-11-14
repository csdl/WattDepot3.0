/**
 * DepositorySensorsServerResource.java created on Nov 13, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.restlet.DepositorySensorsResource;

/**
 * DepositorySensorsServerResource - Handles the Depository sensors HTTP API
 * ("/wattdepot/{group_id}/depository/{depository_id}/sensors/").
 * 
 * @author Cam Moore
 * 
 */
public class DepositorySensorsServerResource extends WattDepotServerResource implements
    DepositorySensorsResource {
  private String depositoryId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.depositoryId = getAttribute("depository_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositorySensorsResource#retrieve()
   */
  @Override
  public List<Sensor> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depository/{" + depositoryId
        + "}/sensors/");
    Depository depository;
    try {
      depository = depot.getWattDeposiory(depositoryId, groupId);
      if (depository != null) {
        return depository.getSensors();
      }
      else {
        setStatus(Status.CLIENT_ERROR_BAD_REQUEST, depositoryId + " not defined.");
      }
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
    }
    return null;
  }

}
