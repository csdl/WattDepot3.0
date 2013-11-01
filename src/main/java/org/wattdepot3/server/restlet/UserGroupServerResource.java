/**
 * UserGroupServerResource.java created on Oct 31, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.UserGroupResource;

/**
 * UserGroupServerResource - Handles the HTTP API
 * ("/wattdepot/{group_id}/usergroup/",
 * "/wattdepot/{group_id}/usergroup/{usergroup_id}").
 * 
 * @author Cam Moore
 * 
 */
public class UserGroupServerResource extends WattDepotServerResource implements UserGroupResource {

  private String userGroupId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.userGroupId = getAttribute("usergroup_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserGroupResource#retrieve()
   */
  @Override
  public UserGroup retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/usergroup/{" + userGroupId + "}");
    return null;
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.restlet.UserGroupResource#store(org.wattdepot3.datamodel
   * .UserGroup)
   */
  @Override
  public void store(UserGroup usergroup) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/usergroup/ with " + usergroup);
    try {
      depot.defineUserGroup(usergroup.getId(), usergroup.getUsers());
    }
    catch (UniqueIdException e) {
      setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserGroupResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/usergroup/{" + userGroupId + "}");
  }

}
