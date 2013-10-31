/**
 * TestWattDepotImpl.java created on Oct 30, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;

/**
 * TestWattDepotImpl - Test cases for the WattDepotImpl.
 * 
 * @author Cam Moore
 * 
 */
public class TestWattDepotImpl {

  private WattDepotImpl impl;

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @Before
  public void setUp() throws Exception {
    impl = new WattDepotImpl();
  }

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for
   * {@link org.wattdepot3.server.depository.impl.hibernate.WattDepotImpl#getUserGroups()}
   * .
   */
  @Test
  public void testGetUserGroups() {
    List<UserGroup> groups = impl.getUserGroups();
    assertNotNull(groups);
    assertTrue(1 == groups.size());
    UserGroup one = groups.get(0);
    assertTrue(UserGroup.ADMIN_GROUP.equals(one));
  }

  /**
   * Test method for
   * {@link org.wattdepot3.server.depository.impl.hibernate.WattDepotImpl#getUsers()}
   * .
   */
  @Test
  public void testGetUsers() {
    List<UserInfo> users = impl.getUsers();
    assertNotNull(users);
    assertTrue(1 == users.size());
    UserInfo one = users.get(0);
    assertTrue(UserInfo.ADMIN.equals(one));
  }

}
