/**
 * UserInfoServerResource.java created on Oct 24, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.UserInfoResource;
import org.wattdepot3.server.WattDepotApplication;

/**
 * UserInfoServerResource - Handles the UserInfo HTTP API
 * ("/wattdepot/{group_id}/user/{user_id}").
 * 
 * @author Cam Moore
 * 
 */
public class UserInfoServerResource extends WattDepotServerResource implements UserInfoResource {
  private String userId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.userId = getAttribute("user_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserInfoResource#retrieve()
   */
  @Override
  public UserInfo retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/user/{" + userId + "}");
    UserInfo user = depot.getUser(userId);
    if (user == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "User " + userId + " is not defined.");
    }
    return user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserInfoResource#store(org.wattdepot3.datamodel
   * .UserInfo)
   */
  @Override
  public void store(UserInfo user) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/user/ with " + user);
    if (!depot.getUsers().contains(user)) {
      try {
        UserInfo defined = depot.defineUserInfo(user.getId(), user.getFirstName(),
            user.getLastName(), user.getEmail(), user.getAdmin(), user.getProperties());
        WattDepotApplication app = (WattDepotApplication) getApplication();
        UserPassword up = app.getDepot().getUserPassword(user.getId());
        if (up != null) {
          MemoryRealm realm = (MemoryRealm) app.getComponent().getRealm("WattDepot Security");
          User newUser = new User(user.getId(), up.getPlainText(), user.getFirstName(),
              user.getLastName(), user.getEmail());
          realm.getUsers().add(newUser);
          realm.map(newUser, app.getRole("User"));
          if (user.getAdmin()) {
            UserGroup.ADMIN_GROUP.add(defined);
            depot.updateUserGroup(UserGroup.ADMIN_GROUP);
          }
        }
        else {
          setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No password information for " + user.getId());
        }
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserInfoResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/user/{" + userId + "}");
    try {
      depot.deleteUser(userId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, e.getMessage());
    }
  }

}
