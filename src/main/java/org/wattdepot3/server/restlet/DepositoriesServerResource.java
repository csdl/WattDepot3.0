/**
 * DepositorysServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.Application;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Depository;
import org.wattdepot.core.restlet.DepositoriesResource;
import org.wattdepot.server.WattDepot;
import org.wattdepot.server.WattDepotApplication;

/**
 * DepositorysServerResource
 * 
 * @author Cam Moore
 * 
 */
public class DepositoriesServerResource extends ServerResource implements DepositoriesResource {

  private String groupId;
  private WattDepot depot;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.groupId = getAttribute("group_id");
    // do some checking?
    WattDepotApplication app = (WattDepotApplication) getApplication();
    this.depot = app.getDepot();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.DepositorysResource#retrieve()
   */
  @Override
  public ArrayList<Depository> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depositories/");
    ArrayList<Depository> ret = new ArrayList<Depository>();
    depot.getWattDepositories();
    ret.add(new Depository("ilima-energy1", "Ilima energy consumption", "energy"));
    return ret;
  }

}
