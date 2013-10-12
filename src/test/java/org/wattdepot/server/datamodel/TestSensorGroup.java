/**
 * TestSensor.java created on Oct 10, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.wattdepot.server.UniqueIdException;
import org.wattdepot.server.depository.impl.jpa.JPAWattDepot;

/**
 * TestSensor - Test cases for the Sensor class.
 *
 * @author Cam Moore
 *
 */
public class TestSensorGroup {

  /**
   * @throws UniqueIdException if there is a problem.
   */
  @Test
  public void test() throws UniqueIdException {
    JPAWattDepot depo = new JPAWattDepot();
    Sensor s = depo.defineSensor("sensor1", "http://foo.com", depo.defineLocation("loc2",
        new Double(21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), depo.defineSensorModel("sm1", "Hammer",
        "hammer", "1.0"));
    
    SensorGroup group = new SensorGroup("group1");
    group.add(s);
    
    System.out.println(group);
    System.out.println(group.toJSON());
    System.out.println(group.toShortJSON());
    fail("Not yet implemented");
  }

}
