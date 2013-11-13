/**
 * DepositoryServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.DepositoryResource;

/**
 * DepositoryServerResource - Handles Depository HTTP API
 * ("/wattdepot/{group_id}/depository/",
 * "/wattdepot/{group_id}/depository/{depository_id}").
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryServerResource extends WattDepotServerResource implements DepositoryResource {

  /** The depository_id in the request. */
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
   * @see org.wattdepot3.restlet.DepositoryResource#retrieve()
   */
  @Override
  public Depository retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depository/{" + depositoryId + "}");
    try {
      return depot.getWattDeposiory(depositoryId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, e.getMessage());
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryResource#store(org.wattdepot3.
   * datamodel.Depository)
   */
  @Override
  public void store(Depository depository) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/depository/ with " + depository);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      try {
        depot.defineWattDepository(depository.getName(), depository.getMeasurementType(), owner);
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/depository/{" + depositoryId + "}");
    try {
      depot.deleteWattDepository(depositoryId, groupId);
    }
    catch (IdNotFoundException | MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
