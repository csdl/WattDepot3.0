/**
 * DepositoryServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.DepositoryResource;

/**
 * DepositoryServerResource - WattDepot 3 Depository Resource handles Depository
 * HTTP API.
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryServerResource extends ServerResource implements DepositoryResource {

  /** The depository_id in the request. */
  private String depositoryId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    // first try GET/POST with data
    this.depositoryId = getQuery().getValues("depository_id");
    if (depositoryId == null) {
      // Then part of the URL
      this.depositoryId = getAttribute("depository_id");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryResource#retrieve()
   */
  @Override
  public Depository retrieve() {
    System.out.println("GET /wattdepot/depository/{" + depositoryId + "}");
    Depository depo = new Depository("First depository", "energy", UserGroup.ADMIN_GROUP);
    return depo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.restlet.DepositoryResource#store(org.wattdepot3.
   * datamodel.Depository)
   */
  @Override
  public void store(Depository depository) {
    System.out.println("PUT /wattdepot/depository/ with " + depository);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/depository/{" + depositoryId + "}");
  }

}
