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
import org.wattdepot.datamodel.TemperatureMeasurement;

/**
 * Tests for the TemperatureMeasurementRepository.
 * 
 * @author Cam Moore
 */
public class TestTemperatureMeasurementRepository {

  /**
   * Test method for
   * {@link org.wattdepot.repository.TemperatureMeasurementRepository#getTemperature(org.wattdepot.datamodel.Meter, java.util.Date)}
   * .
   */
  @Test
  public void testGetTemperatureMeterDate() {
    TemperatureMeasurementRepository repo = new TemperatureMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.TemperatureMeasurementRepository#getTemperature(org.wattdepot.datamodel.Meter, java.util.Date, java.lang.Long)}
   * .
   */
  @Test
  public void testGetTemperatureMeterDateLong() {
    Long gap = 1L;
    TemperatureMeasurementRepository repo = new TemperatureMeasurementRepository();
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
   * Test method for
   * {@link org.wattdepot.repository.TemperatureMeasurementRepository#getMeasurements(org.wattdepot.datamodel.Meter, Date, Date)}
   * .
   */
  @Test
  public void testGetMeasurements() {
    TemperatureMeasurementRepository repo = new TemperatureMeasurementRepository();
    repo.putMeasurement(TestUtils.bTemperatureMeas);
    repo.putMeasurement(TestUtils.aTemperatureMeas);
    Date start = new Date(TestUtils.bDate.getTime() - 5000);
    Date end = new Date(TestUtils.aDate.getTime() + 5000);
    List<TemperatureMeasurement> val = repo.getMeasurements(TestUtils.meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size() + ": " + val, 2 == val.size());
  }

  /**
   * Test method for
   * {@link org.wattdepot.repository.TemperatureMeasurementRepository#getUnit()}.
   */
  @Test
  public void testGetUnits() {
    TemperatureMeasurementRepository repo = new TemperatureMeasurementRepository();
    String units = repo.getUnit();
    String ans = "Degree Fahrenheit";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);    
  }
}
