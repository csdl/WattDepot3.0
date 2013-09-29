/**
 * 
 */
package org.wattdepot.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 * @author Cam Moore
 *
 */
@Entity
public class Meter {
  /**
   * ID for persistence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * The URI to the meter.
   */
  private String uri;
  /**
   * The location of the meter.
   */
  @ManyToOne
  private Location location;
  /**
   * The model of the meter.
   */
  @ManyToOne
  private MeterModel model;
  /**
   * Additional properties of the meter.
   */
  @OneToMany
  private List<Property> properties;
  
  /**
   * Hide the default constructor.
   */
  protected Meter() {
  }
  
  /**
   * @param uri The URI to the meter.
   * @param location The meter's location.
   * @param model The meter's model.
   */
  public Meter(String uri, Location location, MeterModel model) {
    super();
    this.uri = uri;
    this.location = location;
    this.model = model;
    this.properties = new ArrayList<Property>();
  }
  
  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the uri
   */
  public String getUri() {
    return uri;
  }
  /**
   * @param uri the uri to set
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
   * @param location the location to set
   */
  public void setLocation(Location location) {
    this.location = location;
  }
  /**
   * @return the model
   */
  public MeterModel getModel() {
    return model;
  }
  /**
   * @param model the model to set
   */
  public void setModel(MeterModel model) {
    this.model = model;
  }

  /**
   * @return the properties
   */
  public List<Property> getProperties() {
    return properties;
  }

  /**
   * @param key The key.
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
   * @param e The Property to add.
   * @return true if added.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addProperty(Property e) {
    return properties.add(e);
  }

  /**
   * @param o The Property to remove.
   * @return true if removed.
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean removeProperty(Object o) {
    return properties.remove(o);
  }

  /**
   * @param index The index of the property to set.
   * @param element The Property.
   * @return The new Property.
   * @see java.util.List#set(int, java.lang.Object)
   */
  public Property setProperty(int index, Property element) {
    return properties.set(index, element);
  }

  /* (non-Javadoc)
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
  
  /* (non-Javadoc)
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
    Meter other = (Meter) obj;
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
    else if (!propertyEquals(other.properties)) {
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
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Meter [uri=" + uri + ", location=" + location + ", model=" + model + ", properties="
        + properties + "]";
  }

  /**
   * @param obj The List to check.
   * @return true if the list is the same as the properties, false otherwise.
   */
  private boolean propertyEquals(List<?> obj) {
    for (Property p : properties) {
      if (!obj.contains(p)) {
        return false;
      }
    }
    for (Object o : obj) {
      if (!properties.contains(o)) {
        return false;
      }
    }
    return true;
  }
}
