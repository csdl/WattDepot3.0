package org.wattdepot.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Represents a virtual power measurement, an aggregation of power measurements.
 * 
 * @author Cam Moore
 */
@Entity
public class VirtualPowerMeasurement extends VirtualPointMeasurement {
  /**
   * Additional properties of this measurement.
   */
  @OneToMany
  private List<Property> properties;

  /**
   * Hide the default constructor.
   */
  protected VirtualPowerMeasurement() {
  }

  /**
   * @param timestamp
   *          The time the measurement was made.
   * @param value
   *          The Water Counter value.
   * @param meter
   *          The meter making the measurement.
   */
  public VirtualPowerMeasurement(Date timestamp, Double value, VirtualMeter meter) {
    super(timestamp, value, meter);
    this.properties = new ArrayList<Property>();
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
    VirtualPowerMeasurement other = (VirtualPowerMeasurement) obj;
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
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    return result;
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
   *          The index.
   * @param element
   *          The Property to set.
   * @return The Property.
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
    return "VirtualWattMeasurement [timestamp=" + timestamp + ", value=" + value + ", meter="
        + meter + ", properties=" + properties + "]";
  }

}
