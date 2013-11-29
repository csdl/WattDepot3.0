/**
 * MeasurementTypeServerResource.java created on Nov 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.MeasurementType;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.MeasurementTypeResource;

/**
 * MeasurementTypeServerResource - Handles the MeasurementType HTTP API
 * ("/wattdepot/measurementtype/" and
 * "/wattdepot/measurementtype/{measurementtype_id}").
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementTypeServerResource extends WattDepotServerResource implements
    MeasurementTypeResource {

  private String typeSlug;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.typeSlug = getAttribute("measurementtype_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.MeasurementTypeResource#retrieve()
   */
  @Override
  public MeasurementType retrieve() {
    System.out.println("GET /wattdepot/measurementtype/{" + typeSlug + "}");
    MeasurementType mt = null;
    mt = depot.getMeasurementType(typeSlug);
    if (mt == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "MeasurementType " + typeSlug
          + " is not defined.");
    }
    return mt;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.restlet.MeasurementTypeResource#store(org.wattdepot3.datamodel
   * .MeasurementType)
   */
  @Override
  public void store(MeasurementType measurementType) {
    System.out.println("PUT /wattdepot/measurementtype/ with " + measurementType);
    MeasurementType mt = depot.getMeasurementType(measurementType.getId());
    if (mt == null) {
      try {
        depot.defineMeasurementType(measurementType.getName(), measurementType.getUnits());
      }
      catch (UniqueIdException e) {
        setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
      }
    }
    else {
      depot.updateMeasurementType(measurementType);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.MeasurementTypeResource#remove()
   */
  @Override
  public void remove() {
    System.out.println("DEL /wattdepot/measurementtype/{" + typeSlug + "}");
    try {
      depot.deleteMeasurementType(typeSlug);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
