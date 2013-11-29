/**
 * WattDepotAdminInterface.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.IdNotFoundException;

/**
 * WattDepotAdminInterface - Provides all the functionality of the
 * WattDepotInterface adding UserInfo and UserGroup functionality.
 * 
 * @author Cam Moore
 * 
 */
public interface WattDepotAdminInterface extends WattDepotInterface {
  /**
   * Deletes the given User.
   * 
   * @param id
   *          the unique id of the User.
   * @throws IdNotFoundException
   *           if the User's id is not found.
   */
  public void deleteUser(String id) throws IdNotFoundException;

  /**
   * Deletes the given UserGroup.
   * 
   * @param id
   *          the unique id of the UserGroup.
   * @throws IdNotFoundException
   *           if the UserGroup's id is not found.
   */
  public void deleteUserGroup(String id) throws IdNotFoundException;

  /**
   * @param id
   *          the unique id of the UserPassword.
   * @throws IdNotFoundException
   *           if the UserPassword is not found.
   */
  public void deleteUserPassword(String id) throws IdNotFoundException;

  /**
   * Stores the given user in the WattDepot Server.
   * 
   * @param user
   *          The UserInfo to store.
   */
  public void putUser(UserInfo user);

  /**
   * Stores the given UserGroup in the WattDepot Server.
   * 
   * @param group
   *          the UserGroup to store.
   */
  public void putUserGroup(UserGroup group);

  /**
   * Stores the given UserPassword in the WattDepot Server.
   * 
   * @param password
   *          The UserPassword to store.
   */
  public void putUserPassword(UserPassword password);
}
