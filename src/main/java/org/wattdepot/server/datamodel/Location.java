/**
 * Location.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

/**
 * Location - position on the earth.
 *
 * @author Cam Moore
 *
 */
public class Location {
  /** A unique id for the location.  */
  private String id;
  
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
   * @param uniqueId The unique id.
   * @param latitude The decimal Latitude.
   * @param longitude The decimal Longitude.
   * @param altitude The altitude in meters w.r.t. MSL.
   * @param description A String description of the Location.
   */
  public Location(String uniqueId, Double latitude, Double longitude, Double altitude, String description) {
    super();
    this.id = uniqueId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
    this.description = description;
  }
  
  /**
   * @return the uniqueId
   */
  public String id() {
    return id;
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
   * @return The JSON String representation of this Location.
   */
  public String toJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{id :\"");
    buf.append(this.id);
    buf.append("\", \"latitude\": ");
    buf.append(this.latitude);
    buf.append(", \"longitude\": ");
    buf.append(this.longitude);
    buf.append(", \"altitude\": ");
    buf.append(this.altitude);
    buf.append(", \"description\": \"");
    buf.append(this.altitude);
    buf.append("\"}");
    return buf.toString();
  }

}
