/**
 * MeasurementTypesServerResource.java created on Nov 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.wattdepot3.datamodel.MeasurementType;
import org.wattdepot3.datamodel.MeasurementTypeList;
import org.wattdepot3.restlet.MeasurementTypesResource;

/**
 * MeasurementTypesServerResource - ServerResource that handles the URI
 * "/wattdepot/measurementtypes/".
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementTypesServerResource extends WattDepotServerResource implements
    MeasurementTypesResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.MeasurementTypesResource#retrieve()
   */
  @Override
  public MeasurementTypeList retrieve() {
    System.out.println("GET /wattdepot/measurementtypes/");
    MeasurementTypeList list = new MeasurementTypeList();
    for (MeasurementType mt : depot.getMeasurementTypes()) {
      list.getMeasurementTypes().add(mt);
    }
    return list;
  }

}
