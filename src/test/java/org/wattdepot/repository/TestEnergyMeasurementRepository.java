/**
 * 
 */
package org.wattdepot.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.Location;
import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.MeterModel;

/**
 * @author Cam Moore
 *
 */
public class TestEnergyMeasurementRepository {

  private Location loc;
  private MeterModel mm;
  private Meter meter;
  private Date mid;
  private Date bDate;
  private Double bVal;
  private EnergyMeasurement bMeas;
  private Date aDate;
  private Double aVal;
  private EnergyMeasurement aMeas;
  private final Double error = new Double(0.0001);
  
  /**
   * @throws java.lang.Exception if there is a problem.
   */
  @Before
  public void setUp() throws Exception {
    this.loc = new Location(new Double(21.294642), new Double(-157.812727), new Double(30),
        "Hale Aloha Ilima residence hall 6th floor");
    this.mm = new MeterModel("protocol", "type", "1.0");
    this.meter = new Meter("uri", loc, mm);
    this.mid = new Date();
    Long before = mid.getTime() - 1500;
    this.bDate = new Date(before);
    this.bVal = new Double(100);
    this.bMeas = new EnergyMeasurement(bDate, bVal, meter, false);
    Long after = mid.getTime() + 500;
    this.aDate = new Date(after);
    this.aVal = new Double(200);
    this.aMeas = new EnergyMeasurement(aDate, aVal, meter, false);
  }

  /**
   * @throws java.lang.Exception if there is a problem.
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link org.wattdepot.repository.EnergyMeasurementRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetEnergyMeterDateDate() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    repo.putMeasurement(this.bMeas);
    repo.putMeasurement(this.aMeas);
    Date oneThird = new Date(((aDate.getTime() - bDate.getTime()) / 3) + bDate.getTime());
    Date twoThird = new Date(((aDate.getTime() - bDate.getTime()) / 3) * 2 + bDate.getTime());
    try {
      Double ans = new Double(33.3);
      Double value = repo.getEnergy(this.meter, oneThird, twoThird);
      assertEquals("Got " + value + " was expecting " + ans, ans, value, error);
    }
    catch (NoMeasurementException e) {
      fail("Threw NoMeasurementException when we have measurements.");
    }
  }

  /**
   * Test method for {@link org.wattdepot.repository.EnergyMeasurementRepository#getEnergy(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date, java.lang.Long)}.
   */
  @Test
  public void testGetEnergyMeterDateDateLong() {
    Long gap = 1L;
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    repo.putMeasurement(this.bMeas);
    repo.putMeasurement(this.aMeas);
    Date oneThird = new Date(((aDate.getTime() - bDate.getTime()) / 3) + bDate.getTime());
    Date twoThird = new Date(((aDate.getTime() - bDate.getTime()) / 3) * 2 + bDate.getTime());
    try {
      repo.getEnergy(this.meter, oneThird, twoThird, gap);
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
   * Test method for {@link org.wattdepot.repository.EnergyMeasurementRepository#getMeasurements(org.wattdepot.datamodel.Meter, java.util.Date, java.util.Date)}.
   */
  @Test
  public void testGetMeasurements() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    Date start = new Date(bDate.getTime() - 100);
    Date end = new Date(aDate.getTime() + 100);
    List<EnergyMeasurement> val = repo.getMeasurements(meter, start, end);
    assertTrue("Expecting 2 measurments got " + val.size(), 2 == val.size());
  }

  /**
   * Test method for {@link org.wattdepot.repository.EnergyMeasurementRepository#getUnit()}.
   */
  @Test
  public void testGetUnit() {
    EnergyMeasurementRepository repo = new EnergyMeasurementRepository();
    String units = repo.getUnit();
    String ans = "Watt Second";
    assertEquals("Expecting '" + ans + "' got " + units, ans, units);
  }

}
