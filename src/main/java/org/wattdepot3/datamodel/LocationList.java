/**
 * LocationList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * LocationList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class LocationList {
  private ArrayList<Location> locations;
  
  /**
   * Default Constructor.
   */
  public LocationList() {
    locations = new ArrayList<Location>();
  }

  /**
   * @return the locations.
   */
  public ArrayList<Location> getLocations() {
    return locations;
  }

  /**
   * @param locations the locations to set
   */
  public void setLocations(ArrayList<Location> locations) {
    this.locations = locations;
  }

}
