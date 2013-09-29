/**
 * 
 */
package org.wattdepot.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.Meter;

/**
 * Tests for the EnergyMeasurementRepository.
 * 
 * @author Cam Moore
 */
public class TestEnergyMeasurementRepository {

  /**
   * Test method for
   * {@link org.wattdepot.repository.EnergyMeasurementRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}
   * .
   */
  @Test
  public void testGetEnergyMeterDateDate() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.EnergyMeasurementRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date, java.lang.Long)}
   * .
   */
  @Test
  public void testGetEnergyMeterDateDateLong() {
    Long gap = 1L;
    TestUtils.bEnergyMeas.addProperty(TestUtils.bProp);
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.EnergyMeasurementRepository#getMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}
   * .
   */
  @Test
  public void testGetMeasurements() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    repo.putMeasurement(TestUtils.bEnergyMeas);
    repo.putMeasurement(TestUtils.aEnergyMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<EnergyMeasurement> val = repo.getMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
    List<Meter> meters = repo.getMeters();
    assertTrue("Expecting 1 meter got " + meters.size(), 1 == meters.size());
  }

  /**
   * Test method for
   * {@link org.wattdepot.repository.EnergyMeasurementRepository#getUnit()}.
   */
  @Test
  public void testGetUnit() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    String units = repo.getUnit();
    String ans = "Watt Second";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);
  }

}
