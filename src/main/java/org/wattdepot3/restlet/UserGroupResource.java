/**
 * UserGroupResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.wattdepot3.datamodel.UserGroup;

/**
 * UserGroupResource - HTTP Interface for data model UserGroup.
 * 
 * @author Cam Moore
 * 
 */
public interface UserGroupResource {

  /**
   * Defines GET /wattdepot/usergroup/{usergroup_id} API call.
   * 
   * @return The UserGroup with the given id. The id is sent in the request.
   */
  @Get("json") // Use JSON as transport encoding.
  public UserGroup retrieve();

  /**
   * @return The UserGroup as an HTML page.
   */
  @Get("xml | html")
  public Representation toHtml();
  
  /**
   * Defines the PUT /wattdepot/usergroup/ API call.
   * 
   * @param usergroup
   *          The UserGroup to store.
   */
  @Put
  public void store(UserGroup usergroup);

  /**
   * Defined the DEL /wattdepot/usergroup/{usergroup_id} API call. The id is sent in the
   * request.
   */
  @Delete
  public void remove();

}
