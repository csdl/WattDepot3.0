/**
 * DepositoryValueResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.Sensor;

/**
 * DepositorySensorsResource - HTTP Interface for getting the Sensors that have
 * contributed measurements to the Depository.
 * 
 * @author Cam Moore
 * 
 */
public interface DepositorySensorsResource {

  /**
   * Defines GET /wattdepot/depository/{depository_id}/sensors/ API call.
   * 
   * @return An ArrayList of the Sensors that have sent measurements to the depository.
   */
  @Get("json")
  // Use JSON ast transport encoding.
  public ArrayList<Sensor> retrieve();
}
