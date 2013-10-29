/**
 * SensorModel.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

/**
 * SensorModel - Information about a Sensor.
 * 
 * @author Cam Moore
 * 
 */
public class SensorModel {
  /** A unique id for the User. */
  private String id;
  /** The protocol this sensor uses. */
  protected String protocol;
  /** The type of the sensor. */
  protected String type;
  /** The version of the sensor model. */
  protected String version;
  /** The owner of this sensor model. */
  private UserGroup owner;

  /**
   * The default constructor.
   */
  public SensorModel() {

  }

  /**
   * @param uniqueId
   *          The unique id.
   * @param protocol
   *          The protocol used by a sensor.
   * @param type
   *          The type of the sensor.
   * @param version
   *          The version the sensor is using.
   * @param owner
   *          the owner of the location.
   */
  public SensorModel(String uniqueId, String protocol, String type, String version, UserGroup owner) {
    this.id = uniqueId;
    this.protocol = protocol;
    this.type = type;
    this.version = version;
    this.owner = owner;
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
    SensorModel other = (SensorModel) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    if (protocol == null) {
      if (other.protocol != null) {
        return false;
      }
    }
    else if (!protocol.equals(other.protocol)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    }
    else if (!type.equals(other.type)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    }
    else if (!version.equals(other.version)) {
      return false;
    }
    return true;
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
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
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
    result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((version == null) ? 0 : version.hashCode());
    return result;
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
   * @param protocol
   *          the protocol to set
   */
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SensorModel [id=" + id + ", protocol=" + protocol + ", type=" + type + ", version="
        + version + "]";
  }

}
