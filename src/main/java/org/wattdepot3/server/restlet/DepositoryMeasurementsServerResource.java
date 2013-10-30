/**
 * DepositoryMeasurementsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.restlet.DepositoryMeasurementsResource;

/**
 * DepositoryMeasurementsServerResource - Server Resource that returns the Measurements.
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
   * @see org.wattdepot3.restlet.DepositoryMeasurementsResource#retrieve()
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
          "Hale Aloha Ilima residence hall 7th floor", UserGroup.ADMIN_GROUP), new SensorModel(
          "sm1", "Hammer", "hammer", "1.0", UserGroup.ADMIN_GROUP), UserGroup.ADMIN_GROUP);

      Timestamp now = new Timestamp(new Date().getTime());
      ret.add(new Measurement(s, now, new Double(102.3), "energy"));
      return ret;

    }
    // can't get measurements for bad url. TODO: set error code on response.
    return null;
  }

}
