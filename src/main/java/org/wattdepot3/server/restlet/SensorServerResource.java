/**
 * SensorServerResource.java created on Oct 17, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorResource;

/**
 * SensorServerResource
 *
 * @author Cam Moore
 *
 */
public class SensorServerResource extends ServerResource implements SensorResource {
  private String sensorId;

  /* (non-Javadoc)
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.sensorId = getAttribute("sensor_id");
  }

  /* (non-Javadoc)
   * @see org.wattdepot.core.restlet.SensorResource#retrieve()
   */
  @Override
  public Sensor retrieve() {
    Sensor s = new Sensor(sensorId, "http://foo.com", new Location("ilima-3", new Double(21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer",
        "hammer", "1.0"));
    return s;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.core.restlet.SensorResource#store(org.wattdepot.core.datamodel.Sensor)
   */
  @Override
  public void store(Sensor sensor) {
    System.out.println("PUT /wattdepot/sensor/ with " + sensor);
  }

  /* (non-Javadoc)
   * @see org.wattdepot.core.restlet.SensorResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/sensor/{" + sensorId + "}");

  }

}
