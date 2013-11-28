/**
 * Measurement.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.Date;

import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;
import org.wattdepot3.util.Slug;

//import javax.measure.unit.Unit;

/**
 * Measurement - represents a measurement from a sensor.
 * 
 * @author Cam Moore
 * 
 */
public class Measurement {
  /** Unique database id. If necessary. */
  private String id;
  private Sensor sensor;
  private Date timestamp;
  private Double value;
  private Amount<?> amount;
  private Unit<?> units;

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
   * @param units
   *          The type of the measurement.
   */
  public Measurement(Sensor sensor, Date timestamp, Double value, Unit<?> units) {
    // Can a sensor create two measurements at the same time with the same type?
    this.id = Slug.slugify(sensor.getId() + timestamp.getTime() + units);
    this.sensor = sensor;
    this.timestamp = new Date(timestamp.getTime());
    this.amount = Amount.valueOf(value, units);
    this.value = amount.getEstimatedValue();
    this.units = units;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
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
    if (units == null) {
      if (other.units != null) {
        return false;
      }
    }
    else if (!units.equals(other.units)) {
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
    return amount.getUnit().toString();
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
    return new Date(timestamp.getTime());
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
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * The String measurementType must be a javax.measure.unit.Unit toString()
   * value.
   * 
   * @see http://jscience.org/api/javax/measure/unit/NonSI.html or
   *      http://jscience.org/api/javax/measure/unit/SI.html
   * 
   * @param measurementType
   *          the measurementType to set
   */
  public void setMeasurementType(String measurementType) {
    this.units = Unit.valueOf(measurementType);
    this.amount = Amount.valueOf(this.value, this.units);
  }

  /**
   * @return The units for this measurement.
   */
  public Unit<?> units() {
    return this.units;
  }

  /**
   * @param sensor
   *          the sensor to set
   */
  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

  /**
   * @param timestamp
   *          the timestamp to set
   */
  public void setDate(Date timestamp) {
    this.timestamp = new Date(timestamp.getTime());
  }

  /**
   * @param value
   *          the value to set
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
        + ", units=" + units + "]";
  }

  // /**
  // * @return The Units for this measurement.
  // */
  // public Unit<?> getType() {
  // return amount.getUnit();
  // }
}
