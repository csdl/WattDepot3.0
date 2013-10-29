/**
 * SensorProcessServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.Location;
import org.wattdepot.core.datamodel.Sensor;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.datamodel.SensorProcess;
import org.wattdepot.core.restlet.SensorProcessResource;

/**
 * SensorProcessServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcessServerResource extends ServerResource implements SensorProcessResource {

  /** The sensorprocess_id from the request. */
  private String sensorProcessId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    // first try GET/POST with data
    this.sensorProcessId = getQuery().getValues("sensorprocess_id");
    if (sensorProcessId == null) {
      // Then part of the URL
      this.sensorProcessId = getAttribute("sensorprocess_id");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorProcessResource#retrieve()
   */
  @Override
  public SensorProcess retrieve() {
    System.out.println("GET /wattdepot/sensorprocess/{" + sensorProcessId + "}");
    Sensor s = new Sensor("sensor1", "http://foo.com", new Location("ilima-3",
        new Double(21.294642), new Double(-157.812727), new Double(40),
        "Hale Aloha Ilima residence hall 7th floor"), new SensorModel("sm1", "Hammer", "hammer",
        "1.0"));
    SensorProcess process = new SensorProcess(sensorProcessId, s, 10L, "ilima-energy-depository");
    return process;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot.core.restlet.SensorProcessResource#store(org.wattdepot.core
   * .datamodel.SensorProcess)
   */
  @Override
  public void store(SensorProcess sensorprocess) {
    System.out.println("PUT /wattdepot/sensorprocess/ with " + sensorprocess);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorProcessResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/sensorprocess/{" + sensorProcessId + "}");
  }

}
