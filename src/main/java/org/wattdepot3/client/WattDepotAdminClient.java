/**
 * WattDepotAdminClient.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.BadCredentialException;
import org.wattdepot3.exception.IdNotFoundException;

/**
 * WattDepotAdminClient - Admin level client.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotAdminClient extends WattDepotClient implements WattDepotAdminInterface {

  /**
   * Creates a new WattDepotAdminClient.
   * 
   * @param serverUri
   *          The URI of the WattDepot server (e.g.
   *          "http://server.wattdepot.org/")
   * @param username
   *          The name of the user. The user must be defined in the WattDepot
   *          server.
   * @param password
   *          The password for the user.
   * @throws BadCredentialException
   *           If the user or password don't match the credentials on the
   *           WattDepot server.
   */
  public WattDepotAdminClient(String serverUri, String username, String password)
      throws BadCredentialException {
    super(serverUri, username, password);
    // TODO Auto-generated constructor stub
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotAdminInterface#deleteUser(java.lang.String)
   */
  @Override
  public void deleteUser(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotAdminInterface#deleteUserGroup(java.lang
   * .String)
   */
  @Override
  public void deleteUserGroup(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotAdminInterface#putUser(org.wattdepot3.datamodel
   * .UserInfo)
   */
  @Override
  public void putUser(UserInfo user) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotAdminInterface#putUserGroup(org.wattdepot3
   * .datamodel.UserGroup)
   */
  @Override
  public void putUserGroup(UserGroup group) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotAdminInterface#putUserPassword(org.wattdepot3
   * .datamodel.UserPassword)
   */
  @Override
  public void putUserPassword(UserPassword password) {
    // TODO Auto-generated method stub

  }

}
