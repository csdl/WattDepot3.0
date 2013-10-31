/**
 * UserGroupsServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.UserGroupsResource;

/**
 * UserGroupsServerResource - Handles the UserGroup HTTP API
 * ("/wattdepot/{group_id}/groups").
 * 
 * @author Cam Moore
 * 
 */
public class UserGroupsServerResource extends WattDepotServerResource implements UserGroupsResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserGroupsResource#retrieve()
   */
  @Override
  public ArrayList<UserGroup> retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/usergroups/");
    // TODO Auto-generated method stub
    return null;
  }

}
