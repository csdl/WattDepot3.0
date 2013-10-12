/**
 * Sensor.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Sensor - Represents the device making measurements.
 * 
 * @author Cam Moore
 * 
 */
public class Sensor {
  /** A unique id for the sensor. */
  protected String id;
  /** The URI to the sensor. */
  protected String uri;
  /** The location of the sensor. */
  protected Location location;
  /** The model of the sensor. */
  protected SensorModel model;
  /** Additional properties of the sensor. */
  protected List<Property> properties;

  /**
   * Hide the default constructor.
   */
  protected Sensor() {
  }

  /**
   * @param uniqueId
   *          The unique id.
   * @param uri
   *          The URI to the meter.
   * @param location
   *          The meter's location.
   * @param model
   *          The meter's model.
   */
  public Sensor(String uniqueId, String uri, Location location, SensorModel model) {
    super();
    this.id = uniqueId;
    this.uri = uri;
    this.location = location;
    this.model = model;
    this.properties = new ArrayList<Property>();
  }

  /**
   * @return the uniqueId
   */
  public String id() {
    return id;
  }

  /**
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  /**
   * @param uri
   *          the uri to set
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @param location
   *          the location to set
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * @return the model
   */
  public SensorModel getModel() {
    return model;
  }

  /**
   * @param model
   *          the model to set
   */
  public void setModel(SensorModel model) {
    this.model = model;
  }

  /**
   * @return the properties
   */
  public List<Property> getProperties() {
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
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((model == null) ? 0 : model.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
    Sensor other = (Sensor) obj;
    if (location == null) {
      if (other.location != null) {
        return false;
      }
    }
    else if (!location.equals(other.location)) {
      return false;
    }
    if (model == null) {
      if (other.model != null) {
        return false;
      }
    }
    else if (!model.equals(other.model)) {
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
    if (uri == null) {
      if (other.uri != null) {
        return false;
      }
    }
    else if (!uri.equals(other.uri)) {
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
    return "Sensor [uniqueId=" + id + ", uri=" + uri + ", location=" + location + ", model="
        + model + ", properties=" + properties + "]";
  }

  /**
   * @return The JSON version of this Sensor with IDs.
   */
  public String toShortJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{\"id\": \"");
    buf.append(this.id);
    buf.append("\", \"uri\": \"");
    buf.append(this.uri);
    buf.append("\", \"locationId\": \"");
    buf.append(this.location.id());
    buf.append("\", \"modelId\": \"");
    buf.append(this.model.id());
    buf.append("\", [");
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
   * @return The JSON version of this Sensor with full representation of Location and SensorModel.
   */
  public String toJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{\"id\": \"");
    buf.append(this.id);
    buf.append("\", \"uri\": \"");
    buf.append(this.uri);
    buf.append("\", \"location\": ");
    buf.append(this.location.toJSON());
    buf.append(", \"model\": ");
    buf.append(this.model.toJSON());
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
