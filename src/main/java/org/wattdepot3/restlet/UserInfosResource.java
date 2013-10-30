/**
 * UsersResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.resource.Get;
import org.wattdepot3.datamodel.UserInfo;

/**
 * UsersResource - The HTTP API for Users.
 * 
 * @author Cam Moore
 * 
 */
public interface UserInfosResource {

  /**
   * Defines the GET /wattdepot/users/ API call.
   * 
   * @return a List of the defined Users.
   */
  @Get("json") // Use JSON as transport encoding.
  public ArrayList<UserInfo> retrieve();

}
