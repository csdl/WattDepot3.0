/**
 * SensorModel.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

/**
 * SensorModel - Information about a Sensor.
 * 
 * @author Cam Moore
 * 
 */
public class SensorModel {
  /** A unique id for the sensor. */
  private String id;
  /** The protocol this meter uses. */
  private String protocol;
  /** The type of the meter. */
  private String type;
  /** The version of the meter model. */
  private String version;

  /** Hide the default constructor. */
  protected SensorModel() {
  }

  /**
   * @param uniqueId
   *          The unique id.
   * @param protocol
   *          The protocol used by a meter.
   * @param type
   *          The type of the meter.
   * @param version
   *          The version the meter is using.
   */
  public SensorModel(String uniqueId, String protocol, String type, String version) {
    super();
    this.id = uniqueId;
    this.protocol = protocol;
    this.type = type;
    this.version = version;
  }

  /**
   * @return the uniqueId
   */
  public String id() {
    return id;
  }


  /**
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * @param protocol
   *          the protocol to set
   */
  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "MeterModel [protocol=" + protocol + ", type=" + type + ", version=" + version + "]";
  }
  
  /**
   * @return The JSON String representation of this SensorModel.
   */
  public String toJSON() {
    StringBuffer buf = new StringBuffer();
    buf.append("{id :\"");
    buf.append(this.id);
    buf.append("\", \"protocol\": \"");
    buf.append(this.protocol);
    buf.append("\", \"type\": \"");
    buf.append(this.type);
    buf.append("\", \"version\": \"");
    buf.append(this.version);
    buf.append("\"}");
    
    return buf.toString();
  }
}
