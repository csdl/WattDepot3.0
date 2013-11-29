/**
 * SensorModelServerResource.java This file is part of WattDepot 3.
 *
 * Copyright (C) 2013  Cam Moore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wattdepot3.server.restlet;

import java.util.logging.Level;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.restlet.SensorModelResource;

/**
 * SensorModelServerResource - Handles the SensorModel HTTP API
 * ("/wattdepot/{group_id}/sensormodel/",
 * "/wattdepot/{group_id}/sensormodel/{sensormodel_id}").
 * 
 * @author Cam Moore
 * 
 */
public class SensorModelServerResource extends WattDepotServerResource implements
    SensorModelResource {

  /** The sensormodel_id from the request. */
  private String sensorModelId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorModelId = getAttribute("sensormodel_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#retrieve()
   */
  @Override
  public SensorModel retrieve() {
    getLogger().log(Level.INFO, "GET /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
    SensorModel model = null;
    try {
      model = depot.getSensorModel(sensorModelId, groupId);
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FORBIDDEN, e.getMessage());
    }
    if (model == null) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, "SensorModel " + sensorModelId
          + " is not defined.");
    }
    return model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#store(org.wattdepot3
   * .datamodel.SensorModel)
   */
  @Override
  public void store(SensorModel sensormodel) {
    getLogger().log(Level.INFO, "PUT /wattdepot/{" + groupId + "}/sensormodel/ with " + sensormodel);
    UserGroup owner = depot.getUserGroup(groupId);
    if (owner != null) {
      if (!depot.getSensorModelIds(groupId).contains(sensormodel.getId())) {
        try {
          depot.defineSensorModel(sensormodel.getName(), sensormodel.getProtocol(),
              sensormodel.getType(), sensormodel.getVersion(), owner);
        }
        catch (UniqueIdException e) {
          setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
        }
      }
      else {
        if (sensormodel.getOwner() == null) {
          sensormodel.setOwner(owner);
        }
        depot.updateSensorModel(sensormodel);
      }
    }
    else {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, groupId + " does not exist.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.SensorModelResource#remove()
   */
  @Override
  public void remove() {
    getLogger().log(Level.INFO, "DEL /wattdepot/{" + groupId + "}/sensormodel/{" + sensorModelId + "}");
    try {
      depot.deleteSensorModel(sensorModelId, groupId);
    }
    catch (IdNotFoundException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_FAILED_DEPENDENCY, e.getMessage());
    }
  }

}
