/**
 * LocationResource.java created on Oct 13, 2013 by Cam Moore.
 */
package org.wattdepot.server.resource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.wattdepot.server.datamodel.Location;

/**
 * LocationResource -
 * 
 * @author Cam Moore
 * 
 */
public class LocationResource extends ServerResource {

	@Get
	public Location retrieve() {

	}

	@Put
	public void store(Location location) {

	}

	@Delete
	public void remove() {

	}
}
