/**
 * 
 */
package org.wattdepot.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Date;

import org.junit.Test;
import org.wattdepot.datamodel.Location;
import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.MeterModel;
import org.wattdepot.datamodel.PowerMeasurement;
import org.wattdepot.server.Server;

/**
 * @author Cam Moore
 *
 */
public class TestPowerMeasurementRepository {
  static {
    Server.getInstance();
  }

  /**
   * Test for PowerMeasurementRepository.
   * @throws NoMeasurementException if there are issues with the measurements.
   */
  @Test
  public void test() throws NoMeasurementException {
    Location l = new Location(new Double(21.294642), new Double(-157.812727), new Double(30),
        "Hale Aloha Ilima residence hall 6th floor");
    MeterModel mm = new MeterModel("protocol", "type", "1.0");
    Meter m = new Meter("uri", l, mm);
    Date measTime = new Date();
    Long before = measTime.getTime() - 1500;
    Date bDate = new Date(before);
    PowerMeasurement bMeas = new PowerMeasurement(bDate, new Double(100), m, false);
    Long after = measTime.getTime() + 500;
    Date aDate = new Date(after);
    PowerMeasurement aMeas = new PowerMeasurement(aDate, new Double(200), m, false);
    PowerMeasurementRepository repo = new PowerMeasurementRepository();
    repo.putPowerMeasurement(bMeas);
    repo.putPowerMeasurement(aMeas);
    Double power = repo.getPower(m, aDate);
    assertNotNull("Didn't get measurement for " + aDate, power);
    assertSame("Didn't get the same measurement", power, aMeas.getValue());
    power = repo.getPower(m, bDate);
    assertNotNull("Didn't get measurement for " + bDate, power);
    assertSame("Didn't get the same measurement", power, bMeas.getValue());
    power = repo.getPower(m, measTime);
    assertEquals("Didn't get the right interpolated value", power, new Double(175));
    
  }

}
