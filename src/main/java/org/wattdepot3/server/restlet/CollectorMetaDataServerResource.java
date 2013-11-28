/**
 * CollectorMetaDataServerResource.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.logging.Level;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.CollectorMetaData;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.CollectorMetaDataResource;

/**
 * CollectorMetaDataServerResource - Handles the CollectorMetaData HTTP API
 * (("/wattdepot/{group_id}/sensorprocess/",
 * "/wattdepot/{group_id}/sensorprocess/{sensorprocess_id}").
 * 
 * @author Cam Moore
 * 
 */
public class CollectorMetaDataServerResource extends WattDepotServerResource implements
    CollectorMetaDataResource {

  /** The sensorprocess_id from the request. */
  private String sensorProcessId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorProcessId = getAttribute("collectormetadata_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.CollectorMetaDataResource#retrieve()
   */
  @Override
  public CollectorMetaData retrieve() {
    getLogger().log(Level.INFO,
        "GET /wattdepot/{" + groupId + "}/collectormetadata/{" + sensorProcessId + "}");
    CollectorMetaData process = null;
    try {
      process = depot.getCollectorMetaData(sensorProcessId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (process == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "CollectorMetaData " + sensorProcessId
          + " is not defined.");
    }
    return process;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.CollectorMetaDataResource#store(org.wattdepot3
   * .datamodel.CollectorMetaData)
   */
  @Override
  public void store(CollectorMetaData sensorprocess) {
    getLogger().log(Level.INFO,
        "PUT /wattdepot/{" + groupId + "}/sensorprocess/ with " + sensorprocess);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getCollectorMetaDataIds(groupId).contains(sensorprocess.getId())) {
        try {
          depot.defineCollectorMetaData(sensorprocess.getName(), sensorprocess.getSensor(),
              sensorprocess.getPollingInterval(), sensorprocess.getDepositoryId(), owner);
        }
        catch (UniqueIdException e) {
          setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
        }
        catch (MissMatchedOwnerException e) {
          setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
        }
      }
      else {
        depot.updateCollectorMetaData(sensorprocess);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.CollectorMetaDataResource#remove()
   */
  @Override
  public void remove() {
    getLogger().log(Level.INFO,
        "DEL /wattdepot/{" + groupId + "}/sensorprocess/{" + sensorProcessId + "}");
    try {
      depot.deleteCollectorMetaData(sensorProcessId, groupId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
