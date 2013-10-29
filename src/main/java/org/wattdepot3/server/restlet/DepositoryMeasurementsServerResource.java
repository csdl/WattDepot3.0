/**
 * DepositoryMeasurementsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Measurement;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.DepositoryMeasurementsResource;
import org.wattdepot.core.util.tstamp.Tstamp;

/**
 * DepositoryMeasurementsServerResource
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryMeasurementsServerResource extends ServerResource implements
    DepositoryMeasurementsResource {
  private String depositoryId;
  private String sensorId;
  private String start;
  private String end;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    this.sensorId = getQuery().getValues("sensor");
    this.start = getQuery().getValues("start");
    this.end = getQuery().getValues("end");
    this.depositoryId = getAttribute("depository_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.DepositoryMeasurementsResource#retrieve()
   */
  @Override
  public ArrayList<Measurement> retrieve() {
    System.out.println("GET /wattdepot/depository/{" + depositoryId + "}/measurements/?sensor={"
        + sensorId + "},start={" + start + "},end={" + end + "}");
    ArrayList<Measurement> ret = new ArrayList<Measurement>();
    if (sensorId != null && start != null && end != null) {
      // have start and end so build the list of measurements.
      Sensor s = new Sensor(sensorId, "http://foo.com", new Location("ilima-3", new Double(
          21.294642), new Double(-157.812727), new Double(40),
          "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
          "1.0"));

      Timestamp now = new Timestamp(new Date().getTime());
      ret.add(new Measurement(s, now, new Double(102.3), "energy"));
      return ret;

    }
    // can't get measurements for bad url. TODO: set error code on response.
    return null;
  }

}
