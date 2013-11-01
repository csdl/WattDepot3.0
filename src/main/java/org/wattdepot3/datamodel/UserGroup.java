/**
 * UserGroup.java created on Oct 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.HashSet;
import java.util.Set;

/**
 * UserGroup - A group of users.
 * 
 * @author Cam Moore
 * 
 */
public class UserGroup {
  /** The name of the admin group. */
  public static final String ADMIN_GROUP_NAME = "admin";
  /** The admin user group. */
  public static final UserGroup ADMIN_GROUP = new UserGroup(ADMIN_GROUP_NAME);

  /** A unique id. */
  protected String id;
  /** The users in this group. */
  protected Set<UserInfo> users;

  static {
    ADMIN_GROUP.add(UserInfo.ADMIN);
  }

  /**
   * The default constructor.
   */
  public UserGroup() {
    users = new HashSet<UserInfo>();
  }

  /**
   * @param id
   *          The id of the UserGroup.
   */
  public UserGroup(String id) {
    this.id = id;
    this.users = new HashSet<UserInfo>();
  }

  /**
   * @param id
   *          The unique id for this group. It must also be unique from any
   *          User's id.
   * @param users
   *          The Users in the group.
   */
  public UserGroup(String id, Set<UserInfo> users) {
    this.id = id;
    this.users = users;
  }

  /**
   * @param e
   *          The User to add.
   * @return true if successful.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean add(UserInfo e) {
    return users.add(e);
  }

  /**
   * @param o
   *          The user to test.
   * @return true if the user is in the group.
   * @see java.util.List#contains(java.lang.Object)
   */
  public boolean contains(Object o) {
    return users.contains(o);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserGroup other = (UserGroup) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    if (users == null) {
      if (other.users != null) {
        return false;
      }
    }
    else if (!users.equals(other.users)) {
      return false;
    }
    return true;
  }

  /**
   * @return The unique id for the UserGroup.
   */
  public String getId() {
    return id;
  }

  /**
   * @return the users.
   */
  public Set<UserInfo> getUsers() {
    return users;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((users == null) ? 0 : users.hashCode());
    return result;
  }

  /**
   * @param o
   *          The User to remove.
   * @return true if successful.
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean remove(Object o) {
    return users.remove(o);
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @param users
   *          the users to set
   */
  public void setUsers(Set<UserInfo> users) {
    this.users = users;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserGroup [id=" + id + ", users=" + users + "]";
  }

}
