/**
 * TestJPAWattDepot.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.wattdepot.server.UniqueIdException;
import org.wattdepot.server.datamodel.Location;
import org.wattdepot.server.datamodel.Sensor;
import org.wattdepot.server.datamodel.SensorGroup;
import org.wattdepot.server.datamodel.SensorModel;
import org.wattdepot.server.depository.WattDepository;

/**
 * TestJPAWattDepot Test cases for JPAWattDepot.
 * 
 * @author Cam Moore
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJPAWattDepot {

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#defineLocation(java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String)}
   * .
   */
  @Test
  public void testDefineLocation() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor");
    try {
      Location l1 = depo.defineLocation("loc1", loc.getLatitude(), loc.getLongitude(),
          loc.getAltitude(), loc.getDescription());
      assertNotNull(l1);
    }
    catch (UniqueIdException e) {
      fail("Shouldn't have thrown exception.");
    }
    try {
      depo.defineLocation("loc1", loc.getLatitude(), loc.getLongitude(), loc.getAltitude(),
          loc.getDescription());
      fail("Should have thrown exception");
    }
    catch (UniqueIdException e) {
      // this is ok
    }
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#defineSensor(java.lang.String, java.lang.String, org.wattdepot.server.datamodel.Location, org.wattdepot.server.datamodel.SensorModel)}
   * .
   */
  @Test
  public void testDefineSensor() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    try {
      Sensor s = depo.defineSensor("sensor1", "http://foo.com", depo.defineLocation("loc2",
          new Double(21.294642), new Double(-157.812727), new Double(40),
          "Hale Aloha Ilima residence hall 7th floor"), depo.defineSensorModel("sm1", "Hammer",
          "hammer", "1.0"));
      assertNotNull(s);
    }
    catch (UniqueIdException e) {
      fail("Sensor should be unique.");
    }
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#defineSensorGroup(java.lang.String, org.wattdepot.server.datamodel.Sensor[])}
   * .
   */
  @Test
  public void testDefineSensorGroup() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<Sensor> ss = depo.getSensors();
    
    try {
      SensorGroup sg = depo.defineSensorGroup("sg1", ss.get(0));
    }
    catch (UniqueIdException e) {
      fail("Sensor group should not exist.");
    }
    

  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#defineSensorModel(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testDefineSensorModel() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    try {
      SensorModel sm = depo.defineSensorModel("sm2", "protocol", "type", "version");
    }
    catch (UniqueIdException e) {
      fail("SensorModel sm2 should be unique.");
    }
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getLocation(java.lang.String)}
   * .
   */
  @Test
  public void testGetLocation() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    Location notHere = depo.getLocation("not here");
    assertNull(notHere);
    Location loc1 = depo.getLocation("loc1");
    assertNotNull(loc1);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getLocations()}
   * .
   */
  @Test
  public void testGetLocations() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<Location> ls = depo.getLocations();
    assertNotNull(ls);
    assertEquals(2, ls.size());
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensor(java.lang.String)}
   * .
   */
  @Test
  public void testGetSensor() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    Sensor notHere = depo.getSensor("not here");
    assertNull(notHere);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensorGroup(java.lang.String)}
   * .
   */
  @Test
  public void testGetSensorGroup() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    SensorGroup missing = depo.getSensorGroup("not here");
    assertNull(missing);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensorGroups()}
   * .
   */
  @Test
  public void testGetSensorGroups() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<SensorGroup> sgs = depo.getSensorGroups();
    assertNotNull(sgs);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensorModel(java.lang.String)}
   * .
   */
  @Test
  public void testGetSensorModel() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    SensorModel notHere = depo.getSensorModel("not here");
    assertNull(notHere);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensorModels()}
   * .
   */
  @Test
  public void testGetSensorModels() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<SensorModel> sms = depo.getSensorModels();
    assertNotNull(sms);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getSensors()}.
   */
  @Test
  public void testGetSensors() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<Sensor> ss = depo.getSensors();
    assertNotNull(ss);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getWattDeposiory(java.lang.String)}
   * .
   */
  @Test
  public void testGetWattDeposiory() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    WattDepository notHere = depo.getWattDeposiory("Not Here");
    assertNull(notHere);
  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#getWattDepositories()}
   * .
   */
  @Test
  public void testGetWattDepositories() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<WattDepository> wds = depo.getWattDepositories();
    assertNotNull(wds);

  }

  /**
   * Test method for
   * {@link org.wattdepot.server.depository.impl.jpa.JPAWattDepot#JPAWattDepot()}
   * .
   */
  @Test
  public void testJPAWattDepot() {
    JPAWattDepot depo = new JPAWattDepot();
    assertNotNull(depo);
    List<Location> locs = depo.getLocations();
    assertNotNull(locs);
    assertEquals(2, locs.size());
  }

}
