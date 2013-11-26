/**
// * SensorProcess.java created on Oct 12, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.HashSet;
import java.util.Set;

/**
 * SensorProcess - Represents a process that queries a Sensor and produces
 * measurements.
 * 
 * @author Cam Moore
 * 
 */
public class CollectorMetaData {
  /** A unique id for the SensorProcess. */
  private String id;
  /** The sensor making the measurements. */
  protected Sensor sensor;
  /** The number of seconds between polls. */
  protected Long pollingInterval;
  /** The id of the depository where the measurements are stored. */
  protected String depositoryId;
  /** Additional properties for the SensorProcess. */
  protected Set<Property> properties;
  /** The owner of this sensor process. */
  private UserGroup owner;

  /**
   * Hide the default constructor.
   */
  protected CollectorMetaData() {

  }

  /**
   * @param id
   *          The unique id.
   * @param sensor
   *          The sensor that measures the environment.
   * @param poll
   *          The number of seconds between polls.
   * @param depositoryId
   *          The depository_id where measurements are stored.
   * @param owner
   *          the owner of the sensor process.
   */
  public CollectorMetaData(String id, Sensor sensor, Long poll, String depositoryId, UserGroup owner) {
    this.id = id;
    this.sensor = sensor;
    this.pollingInterval = poll;
    this.depositoryId = depositoryId;
    this.properties = new HashSet<Property>();
    this.owner = owner;
  }

  /**
   * @param e
   *          The Property to add.
   * @return true if added.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addProperty(Property e) {
    return properties.add(e);
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
    CollectorMetaData other = (CollectorMetaData) obj;
    if (depositoryId == null) {
      if (other.depositoryId != null) {
        return false;
      }
    }
    else if (!depositoryId.equals(other.depositoryId)) {
      return false;
    }
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
    if (pollingInterval == null) {
      if (other.pollingInterval != null) {
        return false;
      }
    }
    else if (!pollingInterval.equals(other.pollingInterval)) {
      return false;
    }
    if (properties == null) {
      if (other.properties != null) {
        return false;
      }
    }
    else if (!properties.equals(other.properties)) {
      return false;
    }
    if (sensor == null) {
      if (other.sensor != null) {
        return false;
      }
    }
    else if (!sensor.equals(other.sensor)) {
      return false;
    }
    return true;
  }

  /**
   * @return the depositoryId
   */
  public String getDepositoryId() {
    return depositoryId;
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
   * @return the pollingInterval.
   */
  public Long getPollingInterval() {
    return pollingInterval;
  }

  /**
   * @return the properties.
   */
  public Set<Property> getProperties() {
    return properties;
  }

  /**
   * @param key
   *          The key.
   * @return The value of associated with the key.
   */
  public Property getProperty(String key) {
    for (Property p : properties) {
      if (p.getKey().equals(key)) {
        return p;
      }
    }
    return null;
  }

  /**
   * @return the sensor
   */
  public Sensor getSensor() {
    return sensor;
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
    result = prime * result + ((depositoryId == null) ? 0 : depositoryId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((owner == null) ? 0 : owner.hashCode());
    result = prime * result + ((pollingInterval == null) ? 0 : pollingInterval.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
    return result;
  }

  /**
   * Determines if the given group is the owner of this location.
   * 
   * @param group
   *          the UserGroup to check.
   * @return True if the group owns the Location or the group is the
   *         ADMIN_GROUP.
   */
  public boolean isOwner(UserGroup group) {
    if (owner != null) {
      if (owner.equals(group) || group.equals(UserGroup.ADMIN_GROUP)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param o
   *          The Property to remove.
   * @return true if removed.
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean removeProperty(Object o) {
    return properties.remove(o);
  }

  /**
   * @param depositoryId
   *          the depositoryId to set
   */
  public void setDepositoryId(String depositoryId) {
    this.depositoryId = depositoryId;
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
   * @param pollingInterval
   *          the pollingInterval to set
   */
  public void setPollingInterval(Long pollingInterval) {
    this.pollingInterval = pollingInterval;
  }

  /**
   * @param properties
   *          the properties to set
   */
  public void setProperties(Set<Property> properties) {
    this.properties = properties;
  }

  /**
   * @param sensor
   *          the sensor to set
   */
  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "CollectorMetaData [id=" + id + ", sensor=" + sensor + ", pollingInterval="
        + pollingInterval + ", depositoryId=" + depositoryId + ", properties=" + properties
        + ", owner=" + owner + "]";
  }

}
