/**
 * 
 */
package org.wattdepot.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
   */
  @Test
  public void test() {
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
    PowerMeasurementRepository repo = PowerMeasurementRepository.getInstance(m);
    repo.storePowerMeasurement(bMeas);
    repo.storePowerMeasurement(aMeas);
    PowerMeasurement pOut = repo.getPowerMeasurement(aDate);
    assertNotNull("Didn't get measurement for " + aDate, pOut);
    assertSame("Didn't get the same measurement", pOut, aMeas);
    pOut = repo.getPowerMeasurement(bDate);
    assertNotNull("Didn't get measurement for " + bDate, pOut);
    assertSame("Didn't get the same measurement", pOut, bMeas);
    pOut = repo.getPowerMeasurement(measTime);
    assertEquals("Didn't get the right interpolated value", pOut.getValue(), new Double(175));
    assertTrue("Didn't get the right interpolated flag", pOut.isInterpolated());
    
  }

}
