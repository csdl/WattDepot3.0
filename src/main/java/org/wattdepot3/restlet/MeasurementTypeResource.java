/**
 * MeasurementTypeResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.MeasurementType;

/**
 * MeasurementTypeResource - HTTP Interface for data model MeasurementType.
 * 
 * @author Cam Moore
 * 
 */
public interface MeasurementTypeResource {

  /**
   * Defines GET /wattdepot/measurementtype/{measurementtype_id} API call.
   * 
   * @return The MeasurementType with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public MeasurementType retrieve();

  /**
   * Defines the PUT /wattdepot/measurementtype/ API call.
   * 
   * @param measurementType
   *          The MeasurementType to store.
   */
  @Put("json")
  public void store(MeasurementType measurementType);

  /**
   * Defined the DEL /wattdepot/measurementtype/{measurementtype_id} API call. The id is sent
   * in the request.
   */
  @Delete
  public void remove();

}
