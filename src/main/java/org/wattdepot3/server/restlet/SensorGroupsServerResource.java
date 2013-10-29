/**
 * SensorGroupsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorGroup;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorGroupsResouce;

/**
 * SensorGroupsServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupsServerResource extends ServerResource implements SensorGroupsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorGroupsResouce#retrieve()
   */
  @Override
  public ArrayList<SensorGroup> retrieve() {
    System.out.println("GET /wattdepot/sensorgroups/");
    Sensor s1 = new Sensor("sensor1", "http://foo.com", new Location("ilima-7", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    Sensor s2 = new Sensor("sensor2", "http://foo.com", new Location("ilima-5", new Double(
        21.294642), new Double(-157.812727), new Double(30),
        "Hale Aloha Ilima residence hall 5th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    SensorGroup group = new SensorGroup("ilima-group1");
    group.add(s1);

    SensorGroup group2 = new SensorGroup("ilima-group2");
    group2.add(s2);
    ArrayList<SensorGroup> list = new ArrayList<SensorGroup>();
    list.add(group);
    list.add(group2);
    return list;
  }

}
