/**
 * UserGroupsResource.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import java.util.ArrayList;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.wattdepot3.datamodel.UserGroup;

/**
 * UserGroupsResource - The HTTP API for UserGroups.
 * 
 * @author Cam Moore
 * 
 */
public interface UserGroupsResource {

  /**
   * Defines the GET /wattdepot/usergroups/ API call.
   * 
   * @return a List of the defined UserGroups.
   */
  @Get("json") // Use JSON as transport encoding.
  public ArrayList<UserGroup> retrieve();

  /**
   * @return The UserGroups as an HTML page.
   */
  @Get("xml | html")
  public Representation toHtml();

}
