/**
 * WattDepotServer.java created on Nov 22, 2013 by Cam Moore.
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
