/**
 * Measurement.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.Date;


/**
 * Measurement - represents a measurement from a sensor.
 * 
 * @author Cam Moore
 * 
 */
public class Measurement {
  /** Unique database id. If necessary. */
  private Long id;
  private Sensor sensor;
  private Date timestamp;
  private Double value;
  private String measurementType;

  /**
   * Hide the default constructor.
   */
  public Measurement() {

  }

  /**
   * @param sensor
   *          The sensor that made the measurement.
   * @param timestamp
   *          The time of the measurement.
   * @param value
   *          The value measured.
   * @param measurementType
   *          The type of the measurement.
   */
  public Measurement(Sensor sensor, Date timestamp, Double value, String measurementType) {
    this.sensor = sensor;
    this.timestamp = timestamp;
    this.value = value;
    this.measurementType = measurementType;
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
    Measurement other = (Measurement) obj;
    if (measurementType == null) {
      if (other.measurementType != null) {
        return false;
      }
    }
    else if (!measurementType.equals(other.measurementType)) {
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
    if (timestamp == null) {
      if (other.timestamp != null) {
        return false;
      }
    }
    else if (!timestamp.equals(other.timestamp)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    }
    else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  /**
   * @return the measurementType
   */
  public String getMeasurementType() {
    return measurementType;
  }

  /**
   * @return the sensor
   */
  public Sensor getSensor() {
    return sensor;
  }

  /**
   * @return the timestamp
   */
  public Date getDate() {
    return timestamp;
  }

  /**
   * @return the value
   */
  public Double getValue() {
    return value;
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
    result = prime * result + ((measurementType == null) ? 0 : measurementType.hashCode());
    result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * @param measurementType the measurementType to set
   */
  public void setMeasurementType(String measurementType) {
    this.measurementType = measurementType;
  }

  /**
   * @param sensor the sensor to set
   */
  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setDate(Date timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @param value the value to set
   */
  public void setValue(Double value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Measurement [sensor=" + sensor + ", timestamp=" + timestamp + ", value=" + value
        + ", measurementType=" + measurementType + "]";
  }

}
