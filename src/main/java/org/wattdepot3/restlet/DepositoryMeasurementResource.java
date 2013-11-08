/**
 * DepositoryMeasurementResource.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Put;
import org.wattdepot3.datamodel.Measurement;

/**
 * DepositoryMeasurementResource - HTTP Interface for storing, deleting, getting
 * a Measurement.
 * 
 * @author Cam Moore
 * 
 */
public interface DepositoryMeasurementResource {

  /**
   * Defines PUT /wattdepot/{group_id}/depository/{depository_id}/measurement/
   * API call.
   * 
   * @param meas
   *          The Measurement to store.
   */
  @Put
  public void store(Measurement meas);

}
