/**
 * MeasuredValue.java created on Oct 18, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

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
  private String measurementType;

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
  public MeasuredValue(String sensorId, Double value, String measurementType) {
    this.sensorId = sensorId;
    this.value = value;
    this.measurementType = measurementType;
  }

  /**
   * @return the sensorId
   */
  protected String getSensorId() {
    return sensorId;
  }

  /**
   * @return the value
   */
  protected Double getValue() {
    return value;
  }

  /**
   * @return the measurementType
   */
  protected String getMeasurementType() {
    return measurementType;
  }

}
