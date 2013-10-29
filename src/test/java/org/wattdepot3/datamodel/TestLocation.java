/**
 * TestLocation.java created on Oct 28, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TestLocation - Tests for the Location Class.
 * 
 * @author Cam Moore
 * 
 */
public class TestLocation {
  private SessionFactory sessionFactory;

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @Before
  public void setUp() throws Exception {
    // A SessionFactory is set up once for an application
    sessionFactory = new Configuration().configure() // configures settings from
                                                     // hibernate.cfg.xml
        .buildSessionFactory();
  }

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @After
  public void tearDown() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  /**
   * Test method for {@link org.wattdepot3.datamodel.Location#Location()}.
   */
  @Test
  public void testLocation() {
    UserGroup foo = new UserGroup("foo");
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor", foo);
    
    assertTrue("foo is the owner of loc", loc.isOwner(foo));
    assertTrue("admin is always the owner", loc.isOwner(UserGroup.ADMIN_GROUP));
    Location loc1 = new Location();
    loc1.setId("loc1");
    loc1.setLatitude(new Double(21.294642));
    loc1.setLongitude(new Double(-157.812727));
    loc1.setAltitude(new Double(30));
    loc1.setDescription("Hale Aloha Ilima residence hall 6th floor");
    loc1.setOwner(foo);
    assertTrue(loc1.equals(loc));
  }

  /**
   * Test the persistence using hibernate.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testPersistence() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor", UserGroup.ADMIN_GROUP);
    loc.setOwner(UserGroup.ADMIN_GROUP);
    session.save(loc);
    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Location").list();
    session.getTransaction().commit();
    session.close();
    assertTrue("Result is missing " + loc, result.contains(loc));
  }

}
