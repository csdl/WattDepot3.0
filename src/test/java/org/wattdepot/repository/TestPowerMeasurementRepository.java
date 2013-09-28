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
import org.wattdepot.datamodel.PowerMeasurement;

/**
 * Tests for the PowerMeasurementRepository.
 * 
 * @author Cam Moore
 */
public class TestPowerMeasurementRepository {

  /**
   * Test method for
   * {@link org.wattdepot.repository.PowerMeasurementRepository#getPower(org.wattdepot.datamodel.Meter, java.util.Date)}
   * .
   */
  @Test
  public void testGetPowerMeterDate() {
    PowerMeasurementRepository repo = new PowerMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.PowerMeasurementRepository#getPower(org.wattdepot.datamodel.Meter, java.util.Date, java.lang.Long)}
   * .
   */
  @Test
  public void testGetPowerMeterDateLong() {
    Long gap = 1L;
    PowerMeasurementRepository repo = new PowerMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.PowerMeasurementRepository#getMeasurements(org.wattdepot.datamodel.Meter, Date, Date)}
   * .
   */
  @Test
  public void testGetMeasurements() {
    PowerMeasurementRepository repo = new PowerMeasurementRepository();
    repo.putMeasurement(TestUtils.bPowerMeas);
    repo.putMeasurement(TestUtils.aPowerMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<PowerMeasurement> val = repo.getMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for
   * {@link org.wattdepot.repository.PowerMeasurementRepository#getUnit()}.
   */
  @Test
  public void testGetUnits() {
    PowerMeasurementRepository repo = new PowerMeasurementRepository();
    String units = repo.getUnit();
    String ans = "Watt";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);    
  }
}
