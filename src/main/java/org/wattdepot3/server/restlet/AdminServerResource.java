/**
 * AdministratorServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;

/**
 * AdministratorServerResource - Administrative interface for WattDepot. It
 * handles the HTTP API ("/wattdepot/{group_id}/").
 * 
 * @author Cam Moore
 * 
 */
public class AdminServerResource extends WattDepotServerResource {

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
  }

  /**
   * @return The admin user interface as an HTML Representation.
   */
  @Get()
  public Representation toHtml() {
    System.out.println("GET /wattdepot/{" + groupId + "}/");
    Map<String, Object> dataModel = new HashMap<String, Object>();
    // get some stuff from the database
    List<UserInfo> users = depot.getUsers();
    List<UserGroup> groups = depot.getUserGroups();
    List<Depository> depos = depot.getWattDepositories(groupId);
    dataModel.put("users", users);
    dataModel.put("groups", groups);
    dataModel.put("groupId", groupId);
    dataModel.put("depositories", depos);
    System.out.println("Defined users = " + users);
    System.out.println("Defined groups = " + groups);
    Representation rep = new ClientResource(LocalReference.createClapReference(getClass()
        .getPackage()) + "/Admin.ftl").get();

    return new TemplateRepresentation(rep, dataModel, MediaType.TEXT_HTML);
  }
}
