/**
 * TestWattDepotRepository.java created on Sep 29, 2013 by Cam Moore.
 */
package org.wattdepot.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.Location;
import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.MeterModel;
import org.wattdepot.datamodel.PowerMeasurement;
import org.wattdepot.datamodel.TemperatureMeasurement;
import org.wattdepot.datamodel.WaterMeasurement;

/**
 * Tests for the WattDepotRepository class.
 *
 * @author Cam Moore
 *
 */
public class TestWattDepotRepository {

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetEnergyMeterDateDate() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bEnergyMeas);
    repo.putMeasurement(TestUtils.aEnergyMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    Date twoThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3) * 2
        + TestUtils.bDate.getTime());
    try {
      Double ans = new Double(33.33333333);
      Double value = repo.getEnergy(TestUtils.meter, oneThird, twoThird);
      assertEquals("Got " + value + " was expecting " + ans, ans, value, TestUtils.error);
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date, java.lang.Long)}.
   */
  @Test
  public void testGetEnergyMeterDateDateLong() {
    WattDepotRepository repo = new WattDepotRepository();
    Long gap = 1L;
    repo.putMeasurement(TestUtils.bEnergyMeas);
    repo.putMeasurement(TestUtils.aEnergyMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    Date twoThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3) * 2
        + TestUtils.bDate.getTime());
    try {
      repo.getEnergy(TestUtils.meter, oneThird, twoThird, gap);
      fail("getEnergy() Should have thrown MeasurementGapExcpetion.");
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
    catch (MeasurementGapException e) {
      // this is expected.
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getEnergyMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetEnergyMeasurements() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bEnergyMeas);
    repo.putMeasurement(TestUtils.aEnergyMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<EnergyMeasurement> val = repo.getEnergyMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
    List<Meter> meters = repo.getMeters();
    assertTrue("Expecting 1 meter got " + meters.size(), 1 == meters.size());    
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getEnergyUnit()}.
   */
  @Test
  public void testGetEnergyUnit() {
    WattDepotRepository repo = new WattDepotRepository();
    String units = repo.getEnergyUnit();
    String ans = "Watt Second";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getLocations()}.
   */
  @Test
  public void testGetLocations() {
    WattDepotRepository repo = new WattDepotRepository();
    List<Location> locations = repo.getLocations();
    assertTrue("Expecting 1 location got " + locations.size(), 1 == locations.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getMeters()}.
   */
  @Test
  public void testGetMeters() {
    WattDepotRepository repo = new WattDepotRepository();
    List<Meter> locations = repo.getMeters();
    assertTrue("Expecting 1 meter got " + locations.size(), 1 == locations.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getMeterModels()}.
   */
  @Test
  public void testGetMeterModels() {
    WattDepotRepository repo = new WattDepotRepository();
    List<MeterModel> locations = repo.getMeterModels();
    assertTrue("Expecting 1 meter got " + locations.size(), 1 == locations.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getPower(org.wattdepot.datamodel.Meter, java.util.Date)}.
   */
  @Test
  public void testGetPowerMeterDate() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bPowerMeas);
    repo.putMeasurement(TestUtils.aPowerMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    try {
      Double ans = new Double(133.33333333);
      Double value = repo.getPower(TestUtils.meter, oneThird);
      assertEquals("Got " + value + " was expecting " + ans, ans, value, TestUtils.error);
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getPower(org.wattdepot.datamodel.Meter, java.util.Date, java.lang.Long)}.
   */
  @Test
  public void testGetPowerMeterDateLong() {
    WattDepotRepository repo = new WattDepotRepository();
    Long gap = 1L;
    repo.putMeasurement(TestUtils.bPowerMeas);
    repo.putMeasurement(TestUtils.aPowerMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    try {
      repo.getPower(TestUtils.meter, oneThird, gap);
      fail("getPower() should have thrown a MeasurementGapException.");
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
    catch (MeasurementGapException e) {
      // this is expected
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getPowerMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetPowerMeasurements() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bPowerMeas);
    repo.putMeasurement(TestUtils.aPowerMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<PowerMeasurement> val = repo.getPowerMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getPowerUnit()}.
   */
  @Test
  public void testGetPowerUnit() {
    WattDepotRepository repo = new WattDepotRepository();
    String units = repo.getPowerUnit();
    String ans = "Watt";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);    
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getTemperature(org.wattdepot.datamodel.Meter, java.util.Date)}.
   */
  @Test
  public void testGetTemperatureMeterDate() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bTemperatureMeas);
    repo.putMeasurement(TestUtils.aTemperatureMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    try {
      Double ans = new Double(133.33333333);
      Double value = repo.getTemperature(TestUtils.meter, oneThird);
      assertEquals("Got " + value + " was expecting " + ans, ans, value, TestUtils.error);
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getTemperature(org.wattdepot.datamodel.Meter, java.util.Date, java.lang.Long)}.
   */
  @Test
  public void testGetTemperatureMeterDateLong() {
    WattDepotRepository repo = new WattDepotRepository();
    Long gap = 1L;
    repo.putMeasurement(TestUtils.bTemperatureMeas);
    repo.putMeasurement(TestUtils.aTemperatureMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    try {
      repo.getTemperature(TestUtils.meter, oneThird, gap);
      fail("getTemperature() should have thrown a MeasurementGapException.");
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
    catch (MeasurementGapException e) {
      // this is expected
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getTemperatureMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetTemperatureMeasurements() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bTemperatureMeas);
    repo.putMeasurement(TestUtils.aTemperatureMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<TemperatureMeasurement> val = repo.getTemperatureMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getTemperatureUnit()}.
   */
  @Test
  public void testGetTemperatureUnit() {
    WattDepotRepository repo = new WattDepotRepository();
    String units = repo.getTemperatureUnit();
    String ans = "Degree Fahrenheit";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);    
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getWater(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetWaterMeterDateDate() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bWaterMeas);
    repo.putMeasurement(TestUtils.aWaterMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    Date twoThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3) * 2
        + TestUtils.bDate.getTime());
    try {
      Double ans = new Double(33.33333333);
      Double value = repo.getWater(TestUtils.meter, oneThird, twoThird);
      assertEquals("Got " + value + " was expecting " + ans, ans, value, TestUtils.error);
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getWater(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date, java.lang.Long)}.
   */
  @Test
  public void testGetWaterMeterDateDateLong() {
    WattDepotRepository repo = new WattDepotRepository();
    Long gap = 1L;
    repo.putMeasurement(TestUtils.bWaterMeas);
    repo.putMeasurement(TestUtils.aWaterMeas);
    Date oneThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3)
        + TestUtils.bDate.getTime());
    Date twoThird = new Date(((TestUtils.aDate.getTime() - TestUtils.bDate.getTime()) / 3) * 2
        + TestUtils.bDate.getTime());
    try {
      repo.getWater(TestUtils.meter, oneThird, twoThird, gap);
      fail("getWater() Should have thrown MeasurementGapExcpetion.");
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
    catch (MeasurementGapException e) {
      // this is expected.
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getWaterMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetWaterMeasurements() {
    WattDepotRepository repo = new WattDepotRepository();
    repo.putMeasurement(TestUtils.bWaterMeas);
    repo.putMeasurement(TestUtils.aWaterMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<WaterMeasurement> val = repo.getWaterMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.WattDepotRepository#getWaterUnit()}.
   */
  @Test
  public void testGetWaterUnit() {
    WattDepotRepository repo = new WattDepotRepository();
    String units = repo.getWaterUnit();
    String ans = "Gallon";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);
  }
}
