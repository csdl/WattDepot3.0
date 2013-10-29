/**
 * TestProperty.java created on Oct 28, 2013 by Cam Moore.
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
 * TestProperty - Test for the Property Class. This includes persistence.
 *
 * @author Cam Moore
 *
 */
public class TestProperty {
  private SessionFactory sessionFactory;

  /**
   * @throws java.lang.Exception if there is a problem.
   */
  @SuppressWarnings("deprecation")
  @Before
  public void setUp() throws Exception {
    // A SessionFactory is set up once for an application
    sessionFactory = new Configuration().configure() // configures settings from
                                                     // hibernate.cfg.xml
        .buildSessionFactory();
  }

  /**
   * @throws java.lang.Exception if there is a problem.
   */
  @After
  public void tearDown() throws Exception {
    if (sessionFactory != null) {
      sessionFactory.close();
    }
  }

  /**
   * 
   */
  @Test
  public void testSetEquals() {
    Property p1 = new Property("CSDL", "True");
    Property p2 = new Property();
    p2.setKey("CSDL");
    p2.setValue("True");
    assertTrue(p1 + " is not equal to " + p2, p1.equals(p2));
  }
  
  /**
   * Test the persistence using hibernate.
   */
  @Test
  public void testPersistence() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Property p1 = new Property("CSDL", "True");
    session.save(p1);
    Property p2 = new Property("School", "UH");
    session.save(p2);
    Property p3 = new Property("School", "HPU");
    session.save(p3);
    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Property").list();
    session.getTransaction().commit();
    session.close();
    assertTrue("Result is missing " + p1, result.contains(p1));
    assertTrue("Result is missing " + p2, result.contains(p2));
    assertTrue("Result is missing " + p3, result.contains(p3));
  }

}
