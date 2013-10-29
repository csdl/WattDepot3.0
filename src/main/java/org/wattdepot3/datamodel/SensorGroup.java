/**
 * SensorGroup.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * SensorGroup represents a group of Sensors. Used for aggregating sensor
 * measurements.
 * 
 * @author Cam Moore
 * 
 */
public class SensorGroup {
  /** The unique id for this group. */
  private String id;
  /** The List of sensors the compose this group. */
  protected ArrayList<Sensor> sensors;
  /** The owner of this sensor model. */
  private UserGroup owner;

  /**
   * Hide the default constructor.
   */
  protected SensorGroup() {

  }

  /**
   * Create a new SensorGroup with the given unique id.
   * 
   * @param uniqueId
   *          The unique id.
   * @param owner
   *          the owner of the location.
   */
  public SensorGroup(String uniqueId, UserGroup owner) {
    this.id = uniqueId;
    this.sensors = new ArrayList<Sensor>();
    this.owner = owner;
  }

  /**
   * @param e
   *          The sensor to add.
   * @return true if successful.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean add(Sensor e) {
    return sensors.add(e);
  }

  /**
   * @param o
   *          The Sensor to test.
   * @return true if the Sensor is in this group.
   * @see java.util.List#contains(java.lang.Object)
   */
  public boolean contains(Object o) {
    return sensors.contains(o);
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the owner
   */
  public UserGroup getOwner() {
    return owner;
  }

  /**
   * @return the sensors
   */
  public ArrayList<Sensor> getSensors() {
    return sensors;
  }

  /**
   * @param o
   *          The Sensor to remove.
   * @return true if successful.
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean remove(Object o) {
    return sensors.remove(o);
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @param owner
   *          the owner to set
   */
  public void setOwner(UserGroup owner) {
    this.owner = owner;
  }

  /**
   * @param sensors
   *          the sensors to set
   */
  public void setSensors(ArrayList<Sensor> sensors) {
    this.sensors = sensors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((owner == null) ? 0 : owner.hashCode());
    result = prime * result + ((sensors == null) ? 0 : sensors.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SensorGroup other = (SensorGroup) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    if (owner == null) {
      if (other.owner != null) {
        return false;
      }
    }
    else if (!owner.equals(other.owner)) {
      return false;
    }
    if (sensors == null) {
      if (other.sensors != null) {
        return false;
      }
    }
    else if (!sensors.equals(other.sensors)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensorGroup [id=" + id + ", sensors=" + sensors + ", owner=" + owner + "]";
  }

}
