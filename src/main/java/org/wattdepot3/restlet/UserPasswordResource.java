/**
 * UserResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.UserPassword;

/**
 * UserResource - HTTP Interface for data model User.
 * 
 * @author Cam Moore
 * 
 */
public interface UserPasswordResource {

  /**
   * Defines GET /wattdepot/userpassword/{user_id} API call.
   * 
   * @return The User with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public UserPassword retrieve();

  /**
   * Defines the PUT /wattdepot/userpassword/ API call.
   * 
   * @param user
   *          The User to store.
   */
  @Put()
  public void store(UserPassword user);

  /**
   * Defined the DEL /wattdepot/userpassword/{user_id} API call. The id is sent in the
   * request.
   */
  @Delete
  public void remove();

}
