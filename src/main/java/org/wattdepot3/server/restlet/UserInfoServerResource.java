/**
 * UserInfoServerResource.java created on Oct 24, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.UserInfo;
import org.wattdepot.core.restlet.UserInfoResource;
import org.wattdepot.server.UniqueIdException;
import org.wattdepot.server.WattDepot;
import org.wattdepot.server.WattDepotApplication;

/**
 * UserInfoServerResource
 * 
 * @author Cam Moore
 * 
 */
public class UserInfoServerResource extends ServerResource implements UserInfoResource {
  /** The WattDepot instance. */
  private WattDepot depot;

  /** The group that manipulating the UserInfo. */
  private String groupId;
  private String userId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    WattDepotApplication app = (WattDepotApplication) getApplication();
    this.depot = app.getDepot();
    this.groupId = getAttribute("group_id");
    this.userId = getAttribute("user_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.UserInfoResource#retrieve()
   */
  @Override
  public UserInfo retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/user/{" + userId + "}");
    return depot.getUser(userId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot.core.restlet.UserInfoResource#store(org.wattdepot.core.datamodel
   * .UserInfo)
   */
  @Override
  public void store(UserInfo user) {
    System.out.println("PUT /wattdepot/{" + groupId + "}/user/ with " + user);
    if (!depot.getUsers().contains(user)) {
      try {
        depot.defineUserInfo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
            user.getPassword(), user.getAdmin());
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.UserInfoResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/{" + groupId + "}/user/{" + userId + "}");
    
  }

}
