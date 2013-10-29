/**
 * SensorProcessesServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.datamodel.SensorProcess;
import org.wattdepot.core.restlet.SensorProcessesResouce;

/**
 * SensorProcessesServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessesServerResource extends ServerResource implements SensorProcessesResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorProcessesResouce#retrieve()
   */
  @Override
  public ArrayList<SensorProcess> retrieve() {
    System.out.println("GET /wattdepot/sensormodels/");
    Sensor s1 = new Sensor("sensor1", "http://foo.com", new Location("ilima-3", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    Sensor s2 = new Sensor("sensor2", "http://foo.com", new Location("ilima-3", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));

    SensorProcess sm1 = new SensorProcess("sp1", s1, 10L, "energy-depository1");
    SensorProcess sm2 = new SensorProcess("ep1", s2, 120L, "power-depository1");
    ArrayList<SensorProcess> list = new ArrayList<SensorProcess>();
    list.add(sm1);
    list.add(sm2);
    return list;
  }
}
