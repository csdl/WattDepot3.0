/**
 * 
 */
package org.wattdepot.datamodel;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * @author Cam Moore
 *
 */
public class DataModel {

  /** JAXBContext for creating XML text.*/
  private static JAXBContext jaxbContext;

  // Create the JAXBContext. Maybe we want this somewhere else?
  static {
    try {
      jaxbContext = JAXBContext.newInstance(org.wattdepot.datamodel.ObjectFactory.class);
    } catch (Exception e) {
      throw new RuntimeException("Couldn't create JAXB context.");
    }
  }
  
  /**
   * @return The XML String representation of the Property.
   * @throws JAXBException if there is an error with marshalling this instance.
   */
  public String toXML() throws JAXBException {
    Marshaller marshaller = jaxbContext.createMarshaller();
    StringWriter writer = new StringWriter();
    marshaller.marshal(this, writer);
    return writer.toString();
  }
  
  /**
   * @return The JSON String representation of this Property.
   * @throws JSONException If there is a problem converting to JSON.
   * @throws JAXBException If there is a problem marshalling this instance.
   */
  public String toJson() throws JSONException, JAXBException {
    JSONObject xmlJSONObj = XML.toJSONObject(toXML());
    return xmlJSONObj.toString();
  }

}
