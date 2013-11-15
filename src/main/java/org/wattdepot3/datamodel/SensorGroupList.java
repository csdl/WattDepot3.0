/**
 * SensorGroupList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * SensorGroupList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class SensorGroupList {
  private ArrayList<SensorGroup> groups;
  
  /**
   * Default Constructor.
   */
  public SensorGroupList() {
    groups = new ArrayList<SensorGroup>();
  }

  /**
   * @return the groups
   */
  public ArrayList<SensorGroup> getGroups() {
    return groups;
  }

  /**
   * @param groups the groups to set
   */
  public void setGroups(ArrayList<SensorGroup> groups) {
    this.groups = groups;
  }

}
