/**
 * SensorsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorsResouce;

/**
 * SensorsServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorsServerResource extends ServerResource implements SensorsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorsResouce#retrieve()
   */
  @Override
  public ArrayList<Sensor> retrieve() {
    System.out.println("GET /wattdepot/sensormodels/");
    Sensor s1 = new Sensor("sensor1", "http://foo.com", new Location("ilima-3", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    Sensor s2 = new Sensor("sensor2", "http://foo.com", new Location("ilima-3", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));

    ArrayList<Sensor> list = new ArrayList<Sensor>();
    list.add(s1);
    list.add(s2);
    return list;
  }
}
