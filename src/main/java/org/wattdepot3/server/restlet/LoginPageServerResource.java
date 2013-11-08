/**
 * AdministratorServerResource.java created on Oct 23, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

/**
 * AdministratorServerResource - Administrative interface for WattDepot. It
 * handles the HTTP API ("/wattdepot/{group_id}/").
 * 
 * @author Cam Moore
 * 
 */
public class LoginPageServerResource extends WattDepotServerResource {

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
    System.out.println("GET /wattdepot/");
    Map<String, Object> dataModel = new HashMap<String, Object>();
    Representation rep = new ClientResource(LocalReference.createClapReference(getClass()
        .getPackage()) + "/LoginForm.ftl").get();

    return new TemplateRepresentation(rep, dataModel, MediaType.TEXT_HTML);
  }
}
