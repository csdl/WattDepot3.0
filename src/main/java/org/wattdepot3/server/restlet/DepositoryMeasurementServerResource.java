/**
 * DepositoryMeasurementServerResource.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.restlet.DepositoryMeasurementResource;

/**
 * DepositoryMeasurementServerResource - Handles the Measurement HTTP API
 * ("/wattdepot/{group_id}/depository/{depository_id}/measurement/").
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryMeasurementServerResource extends WattDepotServerResource implements
    DepositoryMeasurementResource {
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
   * @see
   * org.wattdepot3.restlet.DepositoryMeasurementResource#store(org.wattdepot3
   * .datamodel.Measurement)
   */
  @Override
  public void store(Measurement meas) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/depository/{" + depositoryId
        + "}/measurement/ with " + meas);
    try {
      Depository depository = depot.getWattDeposiory(depositoryId, groupId);
      depository.putMeasurement(meas);
    }
    catch (MissMatchedOwnerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (MeasurementTypeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
