/**
 * 
 */
package org.wattdepot.datamodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a measurement from a meter that has a running counter. (e.g. a
 * water meter reports number gallons that have flowed through the meter).
 * 
 * @author Cam Moore
 */
@Entity
@Table(name = "VIRTUAL_COUNTER_MEASUREMENTS")
public class VirtualCounterMeasurement {
  /**
   * ID for persistence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  /**
   * Time the measurement was made.
   */
  @Temporal(TemporalType.TIMESTAMP)
  protected Date timestamp;
  /**
   * The counter value measured.
   */
  protected Double value;
  /**
   * The meter that made the measurement.
   */
  @ManyToOne
  protected VirtualMeter meter;

  /**
   * Hide the default constructor.
   */
  protected VirtualCounterMeasurement() {
  }

  /**
   * @param timestamp
   *          The time the measurement was made.
   * @param value
   *          The measured counter value.
   * @param meter
   *          The virtual meter that calculated the measurement.
   */
  public VirtualCounterMeasurement(Date timestamp, Double value, VirtualMeter meter) {
    super();
    this.timestamp = new Date(timestamp.getTime());
    this.value = value;
    this.meter = meter;
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
    VirtualCounterMeasurement other = (VirtualCounterMeasurement) obj;
    if (meter == null) {
      if (other.meter != null) {
        return false;
      }
    }
    else if (!meter.equals(other.meter)) {
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
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the meter
   */
  public VirtualMeter getMeter() {
    return meter;
  }

  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
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
    result = prime * result + ((meter == null) ? 0 : meter.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "VirtualCounterMeasurement [timestamp=" + timestamp + ", value=" + value + ", meter="
        + meter + "]";
  }

}
