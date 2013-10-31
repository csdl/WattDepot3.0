/**
 * DepositorysServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.DepositoriesResource;

/**
 * DepositorysServerResource - ServerResource that handles Depositories.
 * 
 * @author Cam Moore
 * 
 */
public class DepositoriesServerResource extends WattDepotServerResource implements
    DepositoriesResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositorysResource#retrieve()
   */
  @Override
  public ArrayList<Depository> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depositories/");
    ArrayList<Depository> ret = new ArrayList<Depository>();
    depot.getWattDepositories(groupId);
    ret.add(new Depository("Ilima energy consumption", "energy", UserGroup.ADMIN_GROUP));
    return ret;
  }

}
