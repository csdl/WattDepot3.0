/**
 * UserGroupsServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;
import java.util.HashSet;

import org.wattdepot3.datamodel.Property;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
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
    ArrayList<UserGroup> ret = new ArrayList<UserGroup>();
    UserGroup g1 = new UserGroup("UH");
    UserInfo i1 = new UserInfo("cmoore", "Cam", "Moore", "cmoore@hawaii.edu", "password", false, new HashSet<Property>());
    g1.add(i1);
    ret.add(g1);
    return ret;
  }

}
