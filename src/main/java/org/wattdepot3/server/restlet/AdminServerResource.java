/**
 * AdministratorServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.security.User;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.MeasurementType;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.server.depository.impl.hibernate.WattDepotImpl;

/**
 * AdministratorServerResource - Administrative interface for WattDepot. It
 * handles the HTTP API ("/wattdepot/{group_id}/").
 * 
 * @author Cam Moore
 * 
 */
public class AdminServerResource extends WattDepotServerResource {

  /**
   * @return The admin user interface as an HTML Representation.
   */
  @Get()
  public Representation toHtml() {
    getLogger().log(Level.INFO, "GET /wattdepot/{" + groupId + "}/");
    if (!isInRole(groupId) && !isInRole("admin")) {
      User user = getClientInfo().getUser();
      UserInfo info = depot.getUser(user.getIdentifier());
      UserGroup group = depot.getUsersGroup(info);
      if (group != null) {
        redirectPermanent("/wattdepot/" + group.getId() + "/");
      }
      else {
        setStatus(Status.CLIENT_ERROR_FORBIDDEN);
      }
    }
    Map<String, Object> dataModel = new HashMap<String, Object>();
    // get some stuff from the database
    List<UserInfo> users = depot.getUsers();
    List<UserGroup> groups = depot.getUserGroups();
    List<Depository> depos = depot.getWattDepositories(groupId);
    List<Location> locs = depot.getLocations(groupId);
    List<Sensor> sensors = depot.getSensors(groupId);
    List<SensorModel> sensorModels = depot.getSensorModels(groupId);
    List<SensorGroup> sensorGroups = depot.getSensorGroups(groupId);
    List<SensorProcess> sensorProcesses = depot.getSensorProcesses(groupId);
    List<MeasurementType> measurementTypes = depot.getMeasurementTypes();
    dataModel.put("users", users);
    dataModel.put("groups", groups);
    dataModel.put("groupId", groupId);
    dataModel.put("depositories", depos);
    dataModel.put("locations", locs);
    dataModel.put("sensors", sensors);
    dataModel.put("sensorgroups", sensorGroups);
    dataModel.put("sensormodels", sensorModels);
    dataModel.put("sensorprocesses", sensorProcesses);
    dataModel.put("measurementtypes", measurementTypes);
    dataModel.put("opens", ((WattDepotImpl) depot).getSessionOpen());
    dataModel.put("closes", ((WattDepotImpl) depot).getSessionClose());
    Representation rep = new ClientResource(LocalReference.createClapReference(getClass()
        .getPackage()) + "/Admin.ftl").get();

    return new TemplateRepresentation(rep, dataModel, MediaType.TEXT_HTML);
  }
}
