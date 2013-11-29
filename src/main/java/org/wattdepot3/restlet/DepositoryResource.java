/**
 * DepositoryResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.Depository;

/**
 * DepositoryResource - HTTP Interface for data model Depository.
 * 
 * @author Cam Moore
 * 
 */
public interface DepositoryResource {
  /**
   * Defines GET /wattdepot/{group_id}/depository/{depository_id} API call.
   * 
   * @return The Depository with the given id.
   */
  @Get("json")
  // Use JSON as transport encoding.
  public Depository retrieve();

  /**
   * Defines the PUT /wattdepot/{group_id}/depository/ API call.
   * 
   * @param depository
   *          The Depository to store.
   */
  @Put
  public void store(Depository depository);

  /**
   * Defined the DEL /wattdepot/{group_id}/depository/{depository_id} API call.
   */
  @Delete
  public void remove();

}
