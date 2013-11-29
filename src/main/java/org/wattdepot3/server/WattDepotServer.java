/**
 * WattDepotServer.java This file is part of WattDepot 3.
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
package org.wattdepot3.server;

import org.wattdepot3.server.depository.impl.hibernate.WattDepotImpl;

/**
 * WattDepotServer - The main class that starts up the WattDepotServer
 * coordinates the different interfaces.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotServer {

  private WattDepotComponent restletServer;
  private WattDepot depot;

  /**
   * Default constructor.
   */
  public WattDepotServer() {
    // Use settings to instantiate the right WattDepot instance.
    this.depot = new WattDepotImpl();
    if (depot.getSessionOpen() != depot.getSessionClose()) {
      throw new RuntimeException("opens and closed mismatched.");
    }
    this.depot.initializeMeasurementTypes();
    if (depot.getSessionOpen() != depot.getSessionClose()) {
      throw new RuntimeException("opens and closed mismatched.");
    }
    this.restletServer = new WattDepotComponent(depot);
  }

  /**
   * Starts the WattDepotServer.
   * 
   * @throws Exception
   *           if there is a problem starting the different servers.
   */
  public void start() throws Exception {
    this.restletServer.start();
  }

  /**
   * Stops the WattDepotServer.
   * 
   * @throws Exception
   *           if there is a problem stopping the different servers.
   */
  public void stop() throws Exception {
    this.restletServer.stop();
  }

  /**
   * @param args
   *          commandline arguments.
   * @throws Exception
   *           if there is a problem starting the components.
   */
  public static void main(String[] args) throws Exception {
    new WattDepotServer().start();
  }

}
