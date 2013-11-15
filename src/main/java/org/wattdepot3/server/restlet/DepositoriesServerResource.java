/**
 * DepositorysServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.DepositoryList;
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
  public DepositoryList retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depositories/");
    DepositoryList list = new DepositoryList();
    for (Depository d : depot.getWattDepositories(groupId)) {
      list.getDepositories().add(d);
    }
    return list;
  }

}
