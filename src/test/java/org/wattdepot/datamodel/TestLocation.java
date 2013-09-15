/**
 * 
 */
package org.wattdepot.datamodel;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.xml.bind.JAXBException;

import org.json.JSONException;
import org.junit.Test;

/**
 * @author Cam Moore
 *
 */
public class TestLocation {

  private final BigDecimal lat1 = new BigDecimal(21.294642), lat2 = new BigDecimal(21.294645);
  private final BigDecimal lon1 = new BigDecimal(-157.812727), lon2 = new BigDecimal(-157.812730);
  private final BigDecimal alt1 = new BigDecimal(100.1), alt2 = new BigDecimal(110.2);
  private final String des1 = "Description1", des2 = "Description2";
  
  private Location loc1, loc2;
  
  /**
   * Tests the toString() method of Property.
   */
  @Test
  public void testPropertyToString() {
    loc1 = new Location(lat1, lon1, alt1, des1);
    String result = "Location [latitude=" + this.lat1 + ", longitude=" + this.lon1 + 
        ", altitude=" + this.alt1 + ", description=" + this.des1 + "]";
    assertEquals("AccessControl toString did not return expected result: " + loc1.toString(), 
        result, loc1.toString());
  }

  /**
   * Tests the toXML() method of Property.
   * @throws JAXBException if there is a problem.
   */
  @Test
  public void testPropertyToXML() throws JAXBException {
    loc1 = new Location(lat1, lon1, alt1, des1);
    String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Location>" +
        "<Latitude>" + this.lat1 + "</Latitude><Longitude>" + this.lon1 + "</Longitude>" +
        "<Altitude>" + this.alt1 + "</Altitude><Description>" + this.des1 + 
        "</Description></Location>";
    assertEquals("Property toXML did not return expected result: " + loc1.toXML(), result,
        loc1.toXML());
  }

  /**
   * Tests the toJson() method of Property.
   * @throws JAXBException if there is a problem.
   * @throws JSONException if there is a problem.
   */
  @Test
  public void testPropertyToJSON() throws JAXBException, JSONException {
    loc1 = new Location(lat1, lon1, alt1, des1);
    String result = "{\"Location\":{\"Description\":\"Description1\",\"Altitude\":100.1," +
    "\"Latitude\":21.294642,\"Longitude\":-157.812727}}";
    assertEquals("Property toJson did not return expected result: " + loc1.toJson(), result,
        loc1.toJson());
  }

}
