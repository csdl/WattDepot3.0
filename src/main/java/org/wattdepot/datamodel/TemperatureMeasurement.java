package org.wattdepot.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Represents a temperature measurement.
 * 
 * @author Cam Moore
 */
@Entity
public class TemperatureMeasurement extends PointMeasurement {
  /**
   * Additional properties of this measurement.
   */
  @OneToMany
  private List<Property> properties;
  /**
   * Is the measurement interpolated.
   */
  private boolean interpolated;

  /**
   * Hide the default constructor.
   */
  protected TemperatureMeasurement() {
  }

  /**
   * Create a new TemperatureMeasurement.
   * 
   * @param timestamp
   *          The time the PowerMeasurement was made.
   * @param value
   *          The Power measured in Watts.
   * @param meter
   *          The meter making the measurement.
   * @param interpolated
   *          Whether the measurement is interpolated.
   */
  public TemperatureMeasurement(Date timestamp, Double value, Meter meter, boolean interpolated) {
    super(timestamp, value, meter);
    this.properties = new ArrayList<Property>();
    this.interpolated = interpolated;
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
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    TemperatureMeasurement other = (TemperatureMeasurement) obj;
    if (interpolated != other.interpolated) {
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
    return true;
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
   * @return The value associated with the key.
   */
  public Property getProperty(String key) {
    for (Property p : properties) {
      if (p.getKey().equals(key)) {
        return p;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (interpolated ? 1231 : 1237);
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    return result;
  }

  /**
   * @return true if the measurement is interpolated, false if it is a direct measurment.
   */
  public boolean isInterpolated() {
    return interpolated;
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
   *          The index to set.
   * @param element
   *          The Property to set.
   * @return The changed Property.
   * @see java.util.List#set(int, java.lang.Object)
   */
  public Property setProperty(int index, Property element) {
    return properties.set(index, element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "PowerMeasurement [timestamp=" + timestamp + ", value=" + value + ", meter=" + meter
        + ", properties=" + properties + ", interpolated=" + interpolated + "]";
  }

}
