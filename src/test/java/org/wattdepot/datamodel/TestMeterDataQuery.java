/**
 * 
 */
package org.wattdepot.datamodel;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * @author Cam Moore
 * 
 */
public class TestMeterDataQuery {
  private final Location loc1 = TestObjectFactory.buildLocation1(), loc2 = TestObjectFactory
      .buildLocation2();
  private final MeterModel mod1 = TestObjectFactory.buildMeterModel1(), mod2 = TestObjectFactory
      .buildMeterModel2();

  /**
   * Tests the toString() method of Property.
   * 
   * @throws Exception
   */
  @Test
  public void testToString() throws Exception {
    String result = "";
    assertEquals("Measurement toString did not return expected result: " + meas1.toString(),
        result, meas1.toString());
  }

  /**
   * Tests the toXML() method of Property.
   * 
   * @throws Exception
   */
  @Test
  public void testToXML() throws Exception {
    String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    assertEquals("Measurement toXML did not return expected result: " + meas1.toXML(), result,
        meas1.toXML());
  }

  /**
   * Tests the toJson() method of Property.
   * 
   * @throws Exception
   */
  @Test
  public void testToJSON() throws Exception {
    String result = "{\"Measurement\":{\"MeasValue\":1,\"Unit\":{\"Name\":\"Kilowatt Hour\","
        + "\"Abbr\":\"kWh\"},\"Timestamp\":\"" + timestamp1 + "\",\"Properties\":{\"Property\":{"
        + "\"Key\":\"" + key1 + "\",\"PropertyValue\":\"foo\"}}}}";
    assertEquals("Measurements toJson did not return expected result: " + meas1.toJson(), result,
        meas1.toJson());
  }

}
