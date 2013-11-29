/**
 * MeasurementTypesResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.MeasurementTypeList;

/**
 * MeasurementTypesResource - The HTTP API for MeasurementTypes.
 * 
 * @author Cam Moore
 * 
 */
public interface MeasurementTypesResource {

  /**
   * Defines the GET /wattdepot/measurementtypes/ API call.
   * 
   * @return a List of the defined MeasurementTypes.
   */
  @Get("json")  // Use JSON as transport encoding.
  public MeasurementTypeList retrieve();

}
