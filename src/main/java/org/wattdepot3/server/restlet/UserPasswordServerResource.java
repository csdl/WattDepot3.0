/**
 * UserPasswordServerResource.java created on Oct 24, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.UserPasswordResource;

/**
 * UserPasswordServerResource - Handles the UserPassword HTTP API
 * ("/wattdepot/{group_id}/userpassword/{user_id}").
 * 
 * @author Cam Moore
 * 
 */
public class UserPasswordServerResource extends WattDepotServerResource implements
    UserPasswordResource {
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
   * @see org.wattdepot3.restlet.UserPasswordResource#retrieve()
   */
  @Override
  public UserPassword retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/userpassword/{" + userId + "}");
    UserPassword user = depot.getUserPassword(userId);
    if (user == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "User " + userId + " is not defined.");
    }
    else {
      // don't return the plain text password.
      user.setPlainText("********");
    }
    return user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.restlet.UserPasswordResource#store(org.wattdepot3.datamodel
   * .UserPassword)
   */
  @Override
  public void store(UserPassword user) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/userpassword/ with " + user);
    if (!depot.getUserIds().contains(user.getId())) {
      try {
        depot.defineUserPassword(user.getId(), user.getPlainText());
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
    else {
      depot.updateUserPassword(user);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.UserPasswordResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/userpassword/{" + userId + "}");
    try {
      depot.deleteUserPassword(userId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, e.getMessage());
    }
  }

}
