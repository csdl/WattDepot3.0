/**
 * UserGroupsServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.wattdepot.core.datamodel.UserGroup;
import org.wattdepot.core.restlet.UserGroupsResource;
import org.wattdepot.server.WattDepot;
import org.wattdepot.server.WattDepotApplication;

/**
 * UserGroupsServerResource
 * 
 * @author Cam Moore
 * 
 */
public class UserGroupsServerResource extends ServerResource implements UserGroupsResource {
  private WattDepot depot;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    WattDepotApplication app = (WattDepotApplication) getApplication();
    this.depot = app.getDepot();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.UserGroupsResource#retrieve()
   */
  @Override
  public ArrayList<UserGroup> retrieve() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.core.restlet.UserGroupsResource#toHtml()
   */
  @Override
  public Representation toHtml() {
    Map<String, Object> dataModel = new HashMap<String, Object>();

    Representation rep = new ClientResource(LocalReference.createClapReference(getClass()
        .getPackage()) + "/Group.ftl").get();
    
    return new TemplateRepresentation(rep, dataModel, MediaType.TEXT_HTML);
  }

}
