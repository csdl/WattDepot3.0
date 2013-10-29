/**
 * SensorGroupServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorGroup;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorGroupResource;

/**
 * SensorGroupServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroupServerResource extends ServerResource implements SensorGroupResource {

  /** The sensorgroup_id from the request. */
  private String sensorGroupId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    // first try GET/POST with data
    this.sensorGroupId = getQuery().getValues("sensorgroup_id");
    if (sensorGroupId == null) {
      // Then part of the URL
      this.sensorGroupId = getAttribute("sensorgroup_id");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorGroupResource#retrieve()
   */
  @Override
  public SensorGroup retrieve() {
    System.out.println("GET /wattdepot/sensorgroup/{" + sensorGroupId + "}");
    Sensor s1 = new Sensor("sensor1", "http://foo.com", new Location("ilima-7", new Double(
        21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    Sensor s2 = new Sensor("sensor2", "http://foo.com", new Location("ilima-5", new Double(
        21.294642), new Double(-157.812727), new Double(30),
        "Hale Aloha Ilima residence hall 5th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    SensorGroup group = new SensorGroup(sensorGroupId);
    group.add(s1);
    group.add(s2);
    return group;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot.core.restlet.SensorGroupResource#store(org.wattdepot.core
   * .datamodel.SensorGroup)
   */
  @Override
  public void store(SensorGroup sensorgroup) {
    System.out.println("PUT /wattdepot/sensorgroup/ with " + sensorgroup);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorGroupResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/sensorgroup/{" + sensorGroupId + "}");
  }

}
