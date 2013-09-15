/**
 * 
 */
package org.wattdepot.datamodel;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.json.JSONException;
import org.junit.Test;

/**
 * @author Cam Moore
 *
 */
public class TestAtomicSource {

  private final String name1 = "name1", name2 = "name2";

  private AtomicSource source1, source2;
  
  /**
   * Tests the toString() method of Property.
   */
  @Test
  public void testPropertyToString() {
    source1 = new AtomicSource();
    String result = "";
    assertEquals("AccessControl toString did not return expected result: " + source1.toString(), 
        result, source1.toString());
  }

  /**
   * Tests the toXML() method of Property.
   * @throws JAXBException if there is a problem.
   */
  @Test
  public void testPropertyToXML() throws JAXBException {
    source1 = new AtomicSource();
    String result = "";
    assertEquals("Property toXML did not return expected result: " + source1.toXML(), result,
        source1.toXML());
  }

  /**
   * Tests the toJson() method of Property.
   * @throws JAXBException if there is a problem.
   * @throws JSONException if there is a problem.
   */
  @Test
  public void testPropertyToJSON() throws JAXBException, JSONException {
    source1 = new AtomicSource();
    String result = "";
    assertEquals("Property toJson did not return expected result: " + source1.toJson(), result,
        source1.toJson());
  }

}
