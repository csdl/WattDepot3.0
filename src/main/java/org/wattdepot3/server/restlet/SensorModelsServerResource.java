/**
 * SensorModelsServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;

import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.SensorModel;
import org.wattdepot.core.restlet.SensorModelsResouce;

/**
 * SensorModelsServerResource
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelsServerResource extends ServerResource implements SensorModelsResouce {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.SensorModelsResouce#retrieve()
   */
  @Override
  public ArrayList<SensorModel> retrieve() {
    System.out.println("GET /wattdepot/sensormodels/");
    SensorModel sm1 = new SensorModel("sm1", "Hammer", "hammer", "1.0");
    SensorModel sm2 = new SensorModel("eg1", "eGauge", "egauge", "1.0");
    ArrayList<SensorModel> list = new ArrayList<SensorModel>();
    list.add(sm1);
    list.add(sm2);
    return list;
  }
}
