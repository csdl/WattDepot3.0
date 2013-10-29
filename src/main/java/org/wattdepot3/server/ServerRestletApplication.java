/**
 * UserServerApplication.java created on Oct 16, 2013 by Cam Moore.
 */
package org.wattdepot3.server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.wattdepot.server.restlet.DepositoriesServerResource;
import org.wattdepot.server.restlet.DepositoryMeasurementsServerResource;
import org.wattdepot.server.restlet.DepositoryServerResource;
import org.wattdepot.server.restlet.LocationServerResource;
import org.wattdepot.server.restlet.LocationsServerResource;
import org.wattdepot.server.restlet.SensorGroupServerResource;
import org.wattdepot.server.restlet.SensorGroupsServerResource;
import org.wattdepot.server.restlet.SensorModelServerResource;
import org.wattdepot.server.restlet.SensorModelsServerResource;
import org.wattdepot.server.restlet.SensorProcessServerResource;
import org.wattdepot.server.restlet.SensorProcessesServerResource;
import org.wattdepot.server.restlet.SensorServerResource;
import org.wattdepot.server.restlet.SensorsServerResource;

/**
 * UserServerApplication Server app.
 * 
 * @author Cam Moore
 * 
 */
public class ServerRestletApplication extends Application {

  /**
   * @param args
   *          the command line arguments.
   * @throws Exception
   *           if there is a problem.
   */
  public static void main(String[] args) throws Exception {
    Component mailServer = new Component();
    mailServer.getServers().add(Protocol.HTTP, 8111);
    mailServer.getDefaultHost().attach(new ServerRestletApplication());
    mailServer.start();
  }

  /**
   * Creates a root Router to dispatch call to server resources.
   * 
   * @return the inbound root.
   */
  @Override
  public Restlet createInboundRoot() {
    Router router = new Router(getContext());
    router.attach("/wattdepot/depository/", DepositoryServerResource.class);
    router.attach("/wattdepot/depository/{depository_id}", DepositoryServerResource.class);
    router.attach("/wattdepot/depository/{depository_id}/measurements/",
        DepositoryMeasurementsServerResource.class);
    router.attach("/wattdepot/depositories/", DepositoriesServerResource.class);
    router.attach("/wattdepot/location/", LocationServerResource.class);
    router.attach("/wattdepot/location/{location_id}", LocationServerResource.class);
    router.attach("/wattdepot/locations/", LocationsServerResource.class);
    router.attach("/wattdepot/sensorgroup/", SensorGroupServerResource.class);
    router.attach("/wattdepot/sensorgroup/{sensorgroup_id}", SensorGroupServerResource.class);
    router.attach("/wattdepot/sensorgroups/", SensorGroupsServerResource.class);
    router.attach("/wattdepot/sensormodel/", SensorModelServerResource.class);
    router.attach("/wattdepot/sensormodel/{sensormodel_id}", SensorModelServerResource.class);
    router.attach("/wattdepot/sensormodels/", SensorModelsServerResource.class);
    router.attach("/wattdepot/sensorprocess/", SensorProcessServerResource.class);
    router.attach("/wattdepot/sensorprocess/{sensorprocess_id}", SensorProcessServerResource.class);
    router.attach("/wattdepot/sensorprocesses/", SensorProcessesServerResource.class);
    router.attach("/wattdepot/sensor/", SensorServerResource.class);
    router.attach("/wattdepot/sensor/{sensor_id}", SensorServerResource.class);
    router.attach("/wattdepot/sensors/", SensorsServerResource.class);
    return router;
  }
}