/**
 * LoginServerResource.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.wattdepot3.server.WattDepotApplication;
import org.wattdepot3.server.WebSession;

/**
 * LoginServerResource - Handles the login form.
 * 
 * @author Cam Moore
 * 
 */
public class LoginServerResource extends WattDepotServerResource {

  private String username;
  private String password;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    Form foo = new Form(getRequest().getEntity());
    username = foo.getFirstValue("Username");
    password = foo.getFirstValue("Password");
  }

  /**
   * logs user in.
   */
  @Post
  public void login() {
    System.out.println("POST /wattdepot/login/");
    WattDepotApplication app = (WattDepotApplication) getApplication();
    WebSession session = app.createWebSession(username, password);
    String redirectUri = null;
    if (session != null) {
      // add the session cookie.
      getCookieSettings().add(new CookieSetting("sessionToken", session.getId()));
      redirectUri = "/wattdepot/" + session.getGroupId() + "/";
    } 
    else {
      redirectUri = "/wattdepot/";
    }
    if (redirectUri != null) {
      redirectSeeOther(redirectUri);
    }
  }
}
