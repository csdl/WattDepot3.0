/**
 * DepositoryValueServerResource.java created on Oct 22, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.MeasuredValue;
import org.wattdepot.core.restlet.DepositoryValueResource;

/**
 * DepositoryValueServerResource
 *
 * @author Cam Moore
 *
 */
public class DepositoryValueServerResource extends ServerResource implements
    DepositoryValueResource {

  /* (non-Javadoc)
   * @see org.wattdepot.core.restlet.DepositoryValueResource#retrieve()
   */
  @Override
  public MeasuredValue retrieve() {
    // TODO Auto-generated method stub
    return null;
  }

}
