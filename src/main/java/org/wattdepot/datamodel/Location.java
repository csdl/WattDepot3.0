/**
 * 
 */
package org.wattdepot.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Cam Moore
 *
 */
@Entity
@Table( name = "LOCATIONS" )
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * The Location's decimal Latitude.
   */
  private Double latitude;
  /**
   * The Location's decimal Longitude.
   */
  private Double longitude;
  /**
   * The Location's altitude in meters MSL.
   */
  private Double altitude;
  /**
   * The Location's description.
   */
  private String description;
  
  /**
   * Hide the default constructor.
   */
  protected Location() {
  }

  /**
   * @param latitude The decimal Latitude.
   * @param longitude The decimal Longitude.
   * @param altitude The altitude in meters w.r.t. MSL.
   * @param description A String description of the Location.
   */
  public Location(Double latitude, Double longitude, Double altitude, String description) {
    super();
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
    this.description = description;
  }
  
  /**
   * @return the latitude
   */
  public Double getLatitude() {
    return latitude;
  }
  /**
   * @param latitude the latitude to set
   */
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
  /**
   * @return the longitude
   */
  public Double getLongitude() {
    return longitude;
  }
  /**
   * @param longitude the longitude to set
   */
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
  /**
   * @return the altitude
   */
  public Double getAltitude() {
    return altitude;
  }
  /**
   * @param altitude the altitude to set
   */
  public void setAltitude(Double altitude) {
    this.altitude = altitude;
  }
  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((altitude == null) ? 0 : altitude.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
    result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
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
    Location other = (Location) obj;
    if (altitude == null) {
      if (other.altitude != null) {
        return false;
      }   
    }
    else if (!altitude.equals(other.altitude)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    }
    else if (!description.equals(other.description)) {
      return false;
    }
    if (latitude == null) {
      if (other.latitude != null) {
        return false;
      }
    }
    else if (!latitude.equals(other.latitude)) {
      return false;
    }
    if (longitude == null) {
      if (other.longitude != null) {
        return false;
      }
    }
    else if (!longitude.equals(other.longitude)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Location [latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude
        + ", description=" + description + "]";
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
  
}
