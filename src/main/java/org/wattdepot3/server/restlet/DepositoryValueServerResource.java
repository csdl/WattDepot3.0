/**
 * DepositoryValueServerResource.java created on Oct 22, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ServerResource;
import org.wattdepot3.datamodel.MeasuredValue;
import org.wattdepot3.restlet.DepositoryValueResource;

/**
 * DepositoryValueServerResource - ServerResouce that handles the GET
 * /wattdepot/{group_id}/depository/{depository_id}/value/ response.
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryValueServerResource extends ServerResource implements
    DepositoryValueResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryValueResource#retrieve()
   */
  @Override
  public MeasuredValue retrieve() {
    // TODO Auto-generated method stub
    return null;
  }

}
