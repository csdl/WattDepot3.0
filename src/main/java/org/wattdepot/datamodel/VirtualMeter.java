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
import javax.persistence.Table;

/**
 * @author Cam Moore
 * 
 */
@Entity
@Table(name = "VIRTUAL_METERS")
public class VirtualMeter {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * A list of VirtualMeters that help make up this VirtualMeter.
   */
  @OneToMany
  private List<VirtualMeter> virtualMeters;
  /**
   * A list of Meters that help make up this VirtualMeter.
   */
  @OneToMany
  private List<Meter> meters;
  /**
   * The location of the VirtualMeter.
   */
  @ManyToOne
  private Location location;
  /**
   * A description of the VirtualMeter.
   */
  private String description;
  /**
   * Additional properties of the meter.
   */
  @OneToMany
  private List<Property> properties;

  /**
   * @param e
   *          The Meter to add.
   * @return true if added.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addMeter(Meter e) {
    return meters.add(e);
  }

  /**
   * @param e
   *          The VirtualMeter to add.
   * @return true if added.
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addVirtualMeter(VirtualMeter e) {
    return virtualMeters.add(e);
  }

  /**
   * @param o
   *          The Meter to check.
   * @return true if the meter is in the list of meters.
   * @see java.util.List#contains(java.lang.Object)
   */
  public boolean containsMeter(Object o) {
    return meters.contains(o);
  }

  /**
   * @param o
   *          The VirtualMeter to test.
   * @return true if o is in the list of VirtualMeters.
   * @see java.util.List#contains(java.lang.Object)
   */
  public boolean containsVirtualMeter(Object o) {
    return virtualMeters.contains(o);
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
    VirtualMeter other = (VirtualMeter) obj;
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    }
    else if (!description.equals(other.description)) {
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
    if (location == null) {
      if (other.location != null) {
        return false;
      }
    }
    else if (!location.equals(other.location)) {
      return false;
    }
    if (meters == null) {
      if (other.meters != null) {
        return false;
      }
    }
    else if (!meters.equals(other.meters)) {
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
    if (virtualMeters == null) {
      if (other.virtualMeters != null) {
        return false;
      }
    }
    else if (!virtualMeters.equals(other.virtualMeters)) {
      return false;
    }
    return true;
  }

  /**
   * @return All the Meters in this VirtualMeter.
   */
  public List<Meter> getAllMeters() {
    ArrayList<Meter> ret = new ArrayList<Meter>();
    ret.addAll(meters);
    for (VirtualMeter v : virtualMeters) {
      ret.addAll(v.getAllMeters());
    }
    return ret;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @param index
   *          The index.
   * @return The Meter at index.
   * @see java.util.List#get(int)
   */
  public Meter getMeter(int index) {
    return meters.get(index);
  }

  /**
   * @return the meters
   */
  public List<Meter> getMeters() {
    return meters;
  }

  /**
   * @return the virtualMeters
   */
  public List<VirtualMeter> getVirtualMeters() {
    return virtualMeters;
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
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((meters == null) ? 0 : meters.hashCode());
    result = prime * result + ((properties == null) ? 0 : properties.hashCode());
    result = prime * result + ((virtualMeters == null) ? 0 : virtualMeters.hashCode());
    return result;
  }

  /**
   * @param o
   *          The VirtualMeter to remove.
   * @return true if removed.
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean removeVirtualMeter(Object o) {
    return virtualMeters.remove(o);
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param location
   *          the location to set
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * @param index
   *          The index.
   * @param element
   *          The Meter.
   * @return true if set.
   * @see java.util.List#set(int, java.lang.Object)
   */
  public Meter setMeter(int index, Meter element) {
    return meters.set(index, element);
  }

  /**
   * @param meters
   *          the meters to set
   */
  public void setMeters(List<Meter> meters) {
    this.meters = meters;
  }

  /**
   * @param index
   *          The index.
   * @param element
   *          The VirtualMeter.
   * @return The VirtualMeter.
   * @see java.util.List#set(int, java.lang.Object)
   */
  public VirtualMeter setVirtualMeter(int index, VirtualMeter element) {
    return virtualMeters.set(index, element);
  }

  /**
   * @param virtualMeters
   *          the virtualMeters to set
   */
  public void setVirtualMeters(List<VirtualMeter> virtualMeters) {
    this.virtualMeters = virtualMeters;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "VirtualMeter [virtualMeters=" + virtualMeters + ", meters=" + meters + ", location="
        + location + ", description=" + description + ", properties=" + properties + "]";
  }

}
