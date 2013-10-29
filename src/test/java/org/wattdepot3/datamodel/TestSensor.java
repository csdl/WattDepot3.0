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
public class TestSensor {
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
  public void testSensor() {
    UserGroup foo = new UserGroup("foo");
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor", foo);
    SensorModel model = new SensorModel("model1", "protocol1", "type1", "version1", foo);
    Sensor sensor = new Sensor("sensor1", "uri1", loc, model, foo);
    assertTrue(loc.equals(sensor.getLocation()));
  }

  /**
   * Test the persistence using hibernate.
   */
  @SuppressWarnings("unchecked")
  @Test
  public void testPersistence() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    UserGroup foo = new UserGroup("foo");
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor", foo);
    SensorModel model = new SensorModel("model1", "protocol1", "type1", "version1", foo);
    Sensor sensor = new Sensor("sensor1", "uri1", loc, model, foo);
    session.save(foo);
    session.save(loc);
    session.save(model);
    session.save(sensor);
    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Sensor").list();
    for (Sensor s : (List<Sensor>) result) {
      System.out.println(s + ".equals(" + sensor + ") = " + s.equals(sensor));
    }
    assertTrue("Result is missing " + sensor, result.get(0).equals(sensor));
    session.getTransaction().commit();
    session.close();
  }

}
