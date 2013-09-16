/**
 * 
 */
package org.wattdepot.datamodel;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Cam Moore
 *
 */
public class TestMeterModel {

  private final String prot1 = "prot1", prot2 = "prot2";
  private final String type1 = "type1", type2 = "type2";
  private final String ver1 = "ver1", ver2 = "ver2";
  
  private final MeterModel mod1 = new MeterModel(prot1, type1, ver1), 
        mod2 = new MeterModel(prot2, type2, ver2);

  /**
   * Tests the toString() method of Property.
   * @throws Exception 
   */
  @Test
  public void testToString() throws Exception {
    String result = "MeterModel [protocol=" + prot1 + ", type=" + type1 + ", version=" + ver1 + "]";
    assertEquals("MeterModel toString did not return expected result: " + mod1.toString(), 
        result, mod1.toString());
  }

  /**
   * Tests the toXML() method of Property.
   * @throws Exception 
   */
  @Test
  public void testToXML() throws Exception {
    String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MeterModel>" +
        "<Protocol>" + prot1 + "</Protocol><Type>" + type1 + "</Type><Version>" + ver1 + 
        "</Version></MeterModel>";
    assertEquals("MeterModel toXML did not return expected result: " + mod1.toXML(), result,
        mod1.toXML());
  }

  /**
   * Tests the toJson() method of Property.
   * @throws Exception 
   */
  @Test
  public void testToJSON() throws Exception {
    String result = "{\"MeterModel\":{\"Type\":\"" + type1 + "\",\"Version\":\"" + ver1 + "\"," +
        "\"Protocol\":\"" + prot1 + "\"}}";
    assertEquals("MeterModels toJson did not return expected result: " + mod1.toJson(), result,
        mod1.toJson());
  }
}
