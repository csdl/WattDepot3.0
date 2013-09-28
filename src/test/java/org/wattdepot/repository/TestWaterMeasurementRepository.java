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
import org.wattdepot.datamodel.WaterMeasurement;

/**
 * Tests for the WaterMeasurementRepository.
 * 
 * @author Cam Moore
 */
public class TestWaterMeasurementRepository {

  /**
   * Test method for
   * {@link org.wattdepot.repository.WaterMeasurementRepository#getWater(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}
   * .
   */
  @Test
  public void testGetWaterMeterDateDate() {
    WaterMeasurementRepository repo = new WaterMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.WaterMeasurementRepository#getWater(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date, java.lang.Long)}
   * .
   */
  @Test
  public void testGetWaterMeterDateDateLong() {
    Long gap = 1L;
//    TestUtils.bWaterMeas.addProperty(TestUtils.bProp);
    WaterMeasurementRepository repo = new WaterMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.WaterMeasurementRepository#getMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}
   * .
   */
  @Test
  public void testGetMeasurements() {
    WaterMeasurementRepository repo = new WaterMeasurementRepository();
    repo.putMeasurement(TestUtils.bWaterMeas);
    repo.putMeasurement(TestUtils.aWaterMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<WaterMeasurement> val = repo.getMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for
   * {@link org.wattdepot.repository.WaterMeasurementRepository#getUnit()}.
   */
  @Test
  public void testGetUnit() {
    WaterMeasurementRepository repo = new WaterMeasurementRepository();
    String units = repo.getUnit();
    String ans = "Gallon";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);
  }

}
