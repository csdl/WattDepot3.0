/**
 * WattDepotComponent.java created on Oct 21, 2013 by Cam Moore.
 */
package org.wattdepot3.server;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.security.MemoryRealm;
import org.restlet.security.User;
import org.wattdepot3.datamodel.UserInfo;

/**
 * WattDepotComponent - Main class to start the WattDepot3 Server.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotComponent extends Component {

  /**
   * Default constructor. Sets up the WattDepotComponent.
   */
  public WattDepotComponent() {
    setName("WattDepot HTTP API Server");
    setDescription("WattDepot3 RESTful server.");
    setAuthor("Cam Moore");
    // Add a CLAP client connector
    getClients().add(Protocol.CLAP);
    getClients().add(Protocol.FILE);

    // Adds a HTTP server connector
    Server server = getServers().add(Protocol.HTTP, 8119);
    server.getContext().getParameters().set("tracing", "true");

    WattDepotApplication app = new WattDepotApplication();
    getDefaultHost().attachDefault(app);

    // Set up the security realm
    MemoryRealm realm = new MemoryRealm();
    realm.setName("WattDepot Security");
    getRealms().add(realm);
    // add the ADMIN user
    User admin = new User(UserInfo.ADMIN.getId(), UserInfo.ADMIN.getPassword(),
        UserInfo.ADMIN.getFirstName(), UserInfo.ADMIN.getLastName(), UserInfo.ADMIN.getEmail());
    realm.getUsers().add(admin);
    realm.map(admin, app.getRole("ADMIN"));
    realm.map(admin, app.getRole("User"));

    // Set the realm's default enroler and verifier
    app.getContext().setDefaultEnroler(realm.getEnroler());
    app.getContext().setDefaultVerifier(realm.getVerifier());
    
    // Configure the log service
    getLogService().setLoggerName("WattDepot3.AccessLog");
    getLogService().setLogPropertiesRef("clap://system/org/wattdepot3/server/log.properties");
  }

  /**
   * @param args
   *          Command line arguments.
   * @throws Exception
   *           if there is a problem with starting the Component.
   */
  public static void main(String[] args) throws Exception {
    new WattDepotComponent().start();
  }
}
