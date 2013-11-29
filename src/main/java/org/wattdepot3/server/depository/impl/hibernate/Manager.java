/**
 * Manager.java This file is part of WattDepot 3.
 *
 * Copyright (C) 2013  Cam Moore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
