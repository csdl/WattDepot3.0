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
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.UserInfo;
import org.wattdepot.server.WattDepot;
import org.wattdepot.server.WattDepotApplication;

/**
 * AdministratorServerResource
 * 
 * @author Cam Moore
 * 
 */
public class AdminServerResource extends ServerResource {
  /** The WattDepot instance. */
  private WattDepot depot;

  /** The group that is doing the administration. */
  private String groupId;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    WattDepotApplication app = (WattDepotApplication) getApplication();
    this.depot = app.getDepot();
    this.groupId = getAttribute("group_id");
  }

  @Get()
  public Representation toHtml() {

    Map<String, Object> dataModel = new HashMap<String, Object>();
    // get some stuff from the database
    List<UserInfo> users = depot.getUsers();
    dataModel.put("users", users);
    Representation rep = new ClientResource(LocalReference.createClapReference(getClass()
        .getPackage()) + "/Admin.ftl").get();
    
    return new TemplateRepresentation(rep, dataModel, MediaType.TEXT_HTML);
  }
}
