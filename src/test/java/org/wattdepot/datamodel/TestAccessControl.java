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
public class TestAccessControl {
  private final String owner1 = "owner1", owner2="owner2";
  private final boolean priv1 = false, priv2 = true;
  
  private AccessControl acc1, acc2;
  
  /**
   * Tests the toString() method of Property.
   */
  @Test
  public void testPropertyToString() {
    this.acc1 = new AccessControl(this.owner1, this.priv1);
    String result = "AccessControl [owner=" + this.owner1 + ", _public=" + this.priv1 + "]";
    assertEquals("AccessControl toString did not return expected result: " + acc1.toString(), 
        result, acc1.toString());
  }

  /**
   * Tests the toXML() method of Property.
   * @throws JAXBException if there is a problem.
   */
  @Test
  public void testPropertyToXML() throws JAXBException {
    this.acc1 = new AccessControl(this.owner1, this.priv1);
    String result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><AccessControl>" +
    "<Owner>" + this.owner1 + "</Owner><Public>" + this.priv1 + "</Public></AccessControl>";
    assertEquals("Property toXML did not return expected result: " + acc1.toXML(), result,
        acc1.toXML());
  }

  /**
   * Tests the toJson() method of Property.
   * @throws JAXBException if there is a problem.
   * @throws JSONException if there is a problem.
   */
  @Test
  public void testPropertyToJSON() throws JAXBException, JSONException {
    this.acc1 = new AccessControl(this.owner1, this.priv1);
    String result = "{\"AccessControl\":{\"Owner\":\"" + this.owner1 + "\",\"Public\":" + 
    this.priv1 + "}}";
    assertEquals("Property toJson did not return expected result: " + acc1.toJson(), result,
        acc1.toJson());
  }
}
