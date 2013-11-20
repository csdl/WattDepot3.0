/**
 * MeasuredValue.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.Date;

/**
 * MeasuredValue - represents a measured value. Has sensor id, value and
 * measurement type.
 * 
 * @author Cam Moore
 * 
 */
public class MeasuredValue {
  /** The id of the sensor making the measurement. */
  private String sensorId;
  /** The value of the measurement. */
  private Double value;
  /** The type of the measurement. */
  private MeasurementType measurementType;
  /** The time of the measured value. */
  private Date date;

  /**
   * Hide the default constructor.
   */
  protected MeasuredValue() {

  }

  /**
   * Creates a new MeasuredValue.
   * 
   * @param sensorId
   *          The id of the sensor that made the measurement.
   * @param value
   *          The value of the measurement.
   * @param measurementType
   *          The type of the measurement.
   */
  public MeasuredValue(String sensorId, Double value, MeasurementType measurementType) {
    this.sensorId = sensorId;
    this.value = value;
    this.measurementType = measurementType;
  }

  /**
   * @return the sensorId
   */
  public String getSensorId() {
    return sensorId;
  }

  /**
   * @return the value
   */
  public Double getValue() {
    return value;
  }

  /**
   * @return the measurementType
   */
  public MeasurementType getMeasurementType() {
    return measurementType;
  }

  /**
   * @return the date
   */
  public Date getDate() {
    return new Date(date.getTime());
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = new Date(date.getTime());
  }

}
