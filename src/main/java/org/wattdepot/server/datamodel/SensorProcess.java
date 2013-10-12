/**
// * SensorProcess.java created on Oct 12, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * SensorProcess - Represents a process that queries a Sensor and produces
 * measurements.
 * 
 * @author Cam Moore
 * 
 */
public class SensorProcess {

  /** A unique id for the SensorProcess. */
  protected String id;

  /** The sensor making the measurements. */
  protected Sensor sensor;

  /** The number of seconds between polls. */
  protected Long pollingInterval;
  /** Additional properties for the SensorProcess. */
  protected List<Property> properties;

  /**
   * Hide the default constructor.
   */
  protected SensorProcess() {

  }

  /**
   * @param id
   *          The unique id.
   * @param sensor
   *          The sensor that measures the environment.
   * @param poll
   *          The number of seconds between polls.
   */
  public SensorProcess(String id, Sensor sensor, Long poll) {
    super();
    this.id = id;
    this.sensor = sensor;
    this.pollingInterval = poll;
    this.properties = new ArrayList<Property>();
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
  public List<Property> getProperties() {
    return properties;
  }

  /**
   * @return the sensor
   */
  public Sensor getSensor() {
    return sensor;
  }

  /**
   * @return the id
   */
  public String id() {
    return id;
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
  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  /**
   * @param sensor
   *          the sensor to set
   */
  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
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
   * @param e
   *          The Property to add.
   * @return true if added.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addProperty(Property e) {
    return properties.add(e);
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
   * @param index
   *          The index of the property to set.
   * @param element
   *          The Property.
   * @return The new Property.
   * @see java.util.List#set(int, java.lang.Object)
   */
  public Property setProperty(int index, Property element) {
    return properties.set(index, element);
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
    result = prime * result + ((pollingInterval == null) ? 0 : pollingInterval.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
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
    SensorProcess other = (SensorProcess) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensorProcess [id=" + id + ", sensor=" + sensor + ", pollingInterval="
        + pollingInterval + ", properties=" + properties + "]";
  }

  /**
   * @return The JSON version of this SensorProcess with IDs.
   */
  public String toShortJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{\"id\": \"");
    buf.append(this.id);
    buf.append("\", \"sensorId\": \"");
    buf.append(sensor.id());
    buf.append("\", \"pollingInterval\": ");
    buf.append(pollingInterval);
    buf.append(", [");
    for (Property p : this.properties) {
      buf.append("{\"key\": \"" + p.getKey() + "\" \"value\": \"" + p.getValue() + "\"},");
    }
    if (properties.size() > 0) {
      // remove trailing ,
      buf.deleteCharAt(buf.length() - 1);
    }
    buf.append("]}");
    return buf.toString();
  }

  /**
   * @return The JSON version of this SensorProcess with full representation of
   *         the Sensor.
   */
  public String toJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{\"id\": \"");
    buf.append(this.id);
    buf.append("\", \"sensor\": ");
    buf.append(sensor.toJSON());
    buf.append(", \"pollingInterval\": ");
    buf.append(pollingInterval);
    buf.append(", [");
    for (Property p : this.properties) {
      buf.append("{\"key\": \"" + p.getKey() + "\" \"value\": \"" + p.getValue() + "\"},");
    }
    if (properties.size() > 0) {
      // remove trailing ,
      buf.deleteCharAt(buf.length() - 1);
    }
    buf.append("]}");
    return buf.toString();
  }
}
