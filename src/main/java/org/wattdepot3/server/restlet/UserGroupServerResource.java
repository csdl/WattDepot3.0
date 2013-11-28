/**
 * UserGroupServerResource.java created on Oct 31, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.security.MemoryRealm;
import org.restlet.security.Role;
import org.restlet.security.User;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.UserGroupResource;
import org.wattdepot3.server.WattDepotApplication;

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
    UserGroup group = null;
    group = depot.getUserGroup(userGroupId);
    return group;
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
    if (!depot.getUserGroupIds().contains(usergroup.getId())) {
      try {
        UserGroup defined = depot.defineUserGroup(usergroup.getName(), usergroup.getUsers());
        WattDepotApplication app = (WattDepotApplication) getApplication();
        // create the new Role for the group
        String roleName = defined.getId();
        Role role = new Role(roleName);
        app.getRoles().add(role);
        MemoryRealm realm = (MemoryRealm) app.getComponent().getRealm("WattDepot Security");
        for (User user : realm.getUsers()) { // loop through all the Restlet
                                             // users
          for (UserInfo info : defined.getUsers()) {
            if (user.getIdentifier().equals(info.getId())) {
              // assign the user to the role.
              realm.map(user, role);
            }
          }
        }
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
    else {
      depot.updateUserGroup(usergroup);
      // update the Realm
      WattDepotApplication app = (WattDepotApplication) getApplication();
      // create the new Role for the group
      String roleName = usergroup.getId();
      Role role = app.getRole(roleName);
      MemoryRealm realm = (MemoryRealm) app.getComponent().getRealm("WattDepot Security");
      for (UserInfo info : usergroup.getUsers()) {
        realm.map(getUser(info), role);
      }
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
    try {
      depot.deleteUserGroup(userGroupId);
      WattDepotApplication app = (WattDepotApplication) getApplication();
      // create the new Role for the group
      String roleName = userGroupId;
      Role role = app.getRole(roleName);
      MemoryRealm realm = (MemoryRealm) app.getComponent().getRealm("WattDepot Security");
      app.getRoles().remove(role);
      for (User user : realm.getUsers()) {
        realm.findRoles(user).remove(role);
      }
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

  /**
   * @param info
   *          The UserInfo instance.
   * @return The Restlet User that corresponds to the given UserInfo.
   */
  private User getUser(UserInfo info) {
    WattDepotApplication app = (WattDepotApplication) getApplication();
    MemoryRealm realm = (MemoryRealm) app.getComponent().getRealm("WattDepot Security");
    for (User user : realm.getUsers()) { // loop through all the Restlet users
      if (user.getIdentifier().equals(info.getId())) {
        return user;
      }
    }
    return null;
  }
}
