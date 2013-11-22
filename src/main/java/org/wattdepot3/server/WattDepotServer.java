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

	public WattDepotServer() {
		this.restletServer = new WattDepotComponent();
		// Use settings to instantiate the right WattDepot instance.
		this.depot = new WattDepotImpl();
		this.depot.initializeMeasurementTypes();
		((WattDepotApplication) restletServer.getApplication()).setDepot(depot);
	}

	/**
	 * Starts the WattDepotServer.
	 * 
	 * @throws Exception
	 *             if there is a problem starting the different servers.
	 */
	public void start() throws Exception {
		this.restletServer.start();
	}

	/**
	 * Stops the WattDepotServer.
	 * 
	 * @throws Exception
	 *             if there is a problem stopping the different servers.
	 */
	public void stop() throws Exception {
		this.restletServer.stop();
	}

	/**
	 * @param args
	 *            commandline arguments.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new WattDepotServer().start();
	}

}
