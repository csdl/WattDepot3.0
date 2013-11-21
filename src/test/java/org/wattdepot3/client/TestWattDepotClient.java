/**
 * TestWattDepotClient.java created on Nov 20, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.InstanceFactory;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.MeasurementType;
import org.wattdepot3.datamodel.MeasurementTypeList;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorGroupList;
import org.wattdepot3.datamodel.SensorList;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorModelList;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.SensorProcessList;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.NoMeasurementException;

/**
 * TestWattDepotClient - Test cases for the WattDepotClient class.
 * 
 * @author Cam Moore
 * 
 */
public class TestWattDepotClient {

  /** The handle on the client. */
  private WattDepotAdminClient admin;
  private WattDepotClient test;
  private UserInfo testUser = InstanceFactory.getUserInfo();
  private UserPassword testPassword = InstanceFactory.getUserPassword();
  private UserGroup testGroup = InstanceFactory.getUserGroup();

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @Before
  public void setUp() throws Exception {
    System.out.println("setUp()");
    this.admin = new WattDepotAdminClient("http://localhost:8119/", "admin", "admin");
    this.admin.putUserPassword(testPassword);
    this.admin.putUser(testUser);
    this.admin.putUserGroup(testGroup);
    this.admin.putMeasurementType(InstanceFactory.getMeasurementType());
    this.test = new WattDepotClient("http://localhost:8119/", testPassword.getId(),
        testPassword.getPlainText());
  }

  /**
   * @throws java.lang.Exception
   *           if there is a problem.
   */
  @After
  public void tearDown() throws Exception {
    admin.deleteUser(testPassword.getId());
    admin.deleteUserGroup(testGroup.getId());
  }

  /**
   * Test method for
   * {@link org.wattdepot3.client.WattDepotClient#WattDepotClient(java.lang.String, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testWattDepotClient() {
    assertNotNull(this.test);
  }

  /**
   * Test method for Depositories.
   */
  @Test
  public void testDepository() {
    Depository depo = InstanceFactory.getDepository();
    test.putDepository(depo);
    try {
      Depository ret = test.getDepository(depo.getName());
      assertEquals(depo, ret);
      test.deleteDepository(ret);
      try {
        ret = test.getDepository(depo.getName());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + depo);
    }

  }

  /**
   * Test method for Locations.
   */
  @Test
  public void testLocation() {
    Location loc = InstanceFactory.getLocation();
    test.putLocation(loc);
    try {
      Location ret = test.getLocation(loc.getId());
      assertEquals(loc, ret);
      test.deleteLocation(ret);
      try {
        ret = test.getLocation(loc.getId());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + loc);
    }
  }

  /**
   * Test method for MeasurementTypes.
   */
  @Test
  public void testMeasurementType() {
    MeasurementType type = InstanceFactory.getMeasurementType();
    test.putMeasurementType(type);
    MeasurementTypeList list = test.getMeasurementTypes();
    assertTrue(list.getMeasurementTypes().contains(type));
    try {
      MeasurementType ret = test.getMeasurementType(type.getSlug());
      assertEquals(type, ret);
      test.deleteMeasurementType(ret);
      try {
        ret = test.getMeasurementType(type.getSlug());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + type);
    }
  }

  /**
   * Test method for Sensors.
   */
  @Test
  public void testSensor() {
    Sensor sensor = InstanceFactory.getSensor();
    test.putSensor(sensor);
    SensorList list = test.getSensors();
    assertTrue(list.getSensors().contains(sensor));
    try {
      Sensor ret = test.getSensor(sensor.getId());
      assertEquals(sensor, ret);
      test.deleteSensor(ret);
      try {
        ret = test.getSensor(sensor.getId());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + sensor);
    }
  }

  /**
   * Test method for SensorGroups.
   */
  @Test
  public void testSensorGroup() {
    SensorGroup group = InstanceFactory.getSensorGroup();
    test.putSensorGroup(group);
    SensorGroupList list = test.getSensorGroups();
    assertTrue(list.getGroups().contains(group));
    try {
      SensorGroup ret = test.getSensorGroup(group.getId());
      assertEquals(group, ret);
      test.deleteSensorGroup(ret);
      try {
        ret = test.getSensorGroup(group.getId());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + group);
    }
  }

  /**
   * Test method for SensorModels.
   */
  @Test
  public void testSensorModel() {
    SensorModel model = InstanceFactory.getSensorModel();
    test.putSensorModel(model);
    SensorModelList list = test.getSensorModels();
    assertTrue(list.getModels().contains(model));
    try {
      SensorModel ret = test.getSensorModel(model.getId());
      assertEquals(model, ret);
      test.deleteSensorModel(model);
      try {
        ret = test.getSensorModel(model.getId());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + model);
    }
  }

  /**
   * Test method for SensorProcesses.
   */
  @Test
  public void testSensorProcess() {
    SensorProcess model = InstanceFactory.getSensorProcess();
    test.putSensorProcess(model);
    SensorProcessList list = test.getSensorProcesses();
    assertTrue(list.getProcesses().contains(model));
    try {
      SensorProcess ret = test.getSensorProcess(model.getId());
      assertEquals(model, ret);
      test.deleteSensorProcess(model);
      try {
        ret = test.getSensorProcess(model.getId());
        assertNull(ret);
      }
      catch (IdNotFoundException e) {
        // this is what we want.
      }
    }
    catch (IdNotFoundException e) {
      fail("Should have " + model);
    }
  }

  /**
   * Test method for Measurements.
   */
  @Test
  public void testMeasurements() {
    Depository depo = InstanceFactory.getDepository();
    test.putDepository(depo);
    Measurement m1 = InstanceFactory.getMeasurementOne();
    try {
      test.putMeasurement(depo, m1);
      m1 = InstanceFactory.getMeasurementTwo();
      test.putMeasurement(depo, m1);
      Double val = test.getValue(depo, m1.getSensor(), m1.getDate());
      assertTrue(m1.getValue().equals(val));
    }
    catch (MeasurementTypeException e) {
      fail(e.getMessage());
    }
    catch (NoMeasurementException e) {
      fail(e.getMessage());
    }

  }

}
