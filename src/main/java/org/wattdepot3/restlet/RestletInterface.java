/**
 * RestletInterface.java created on Oct 14, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

/**
 * RestletInterface - The base HTTP API interface. Annotated interface
 * representing the contract between the client and the server for the main
 * resources.
 * 
 * @author Cam Moore
 * 
 */
public interface RestletInterface {

  /**
   * The GET Method for JSON data.
   * 
   * @return The requested data as JSON.
   */
  @Get("json")
  public String getJSON();

  /**
   * The PUT method.
   * 
   * @param entity
   *          The entity to store.
   */
  @Put()
  public void store(String entity);
  
  /**
   * The DELETE method.
   */
  @Delete
  public void remove();
}
