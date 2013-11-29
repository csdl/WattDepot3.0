/**
 * Manager.java created on Oct 30, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Manager - provides access to the singleton SessionFactory needed to persist
 * the objects.
 * 
 * @author Cam Moore
 * 
 */
public class Manager {
  private static SessionFactory sessionFactory;

  /**
   * @return The singleton SessionFactory.
   */
  public static SessionFactory getFactory() {
    if (sessionFactory == null) {
      // A SessionFactory is set up once for an application
      sessionFactory = new Configuration().configure() // configures settings
                                                       // from
                                                       // hibernate.cfg.xml
          .buildSessionFactory();
    }
    return sessionFactory;
  }

  //TODO need a way to close the session factory when the program is pau.
}
