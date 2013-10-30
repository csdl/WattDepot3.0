/**
 * DepositorysServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot3.server.WattDepot;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.DepositoriesResource;
import org.wattdepot3.server.WattDepotApplication;

/**
 * DepositorysServerResource - ServerResource that handles Depositories.
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
   * @see org.wattdepot3.restlet.DepositorysResource#retrieve()
   */
  @Override
  public ArrayList<Depository> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depositories/");
    ArrayList<Depository> ret = new ArrayList<Depository>();
    depot.getWattDepositories();
    ret.add(new Depository("Ilima energy consumption", "energy",UserGroup.ADMIN_GROUP));
    return ret;
  }

}
