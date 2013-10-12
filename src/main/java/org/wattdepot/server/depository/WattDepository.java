/**
 * WattDepository.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository;

import java.util.Date;
import java.util.List;

import org.wattdepot.server.datamodel.Measurement;
import org.wattdepot.server.datamodel.Sensor;

/**
 * WattDepository - holds measurements.
 * 
 * @author Cam Moore
 * 
 */
public abstract class WattDepository {
  /** The unique id for the WattDepository.  */
  protected String id;
  /** The name. */
  protected String name;
  /** The type of measurement this depository holds. */
  protected String measurementType;

  /**
   * @return The id of this WattDepository.
   */
  public String id() {
    return id;
  }
  
  /**
   * Returns a List of the Measurements for the given sensor and time interval.
   * 
   * @param sensor
   *          The sensor that made the measurements.
   * @param start
   *          The start of the interval.
   * @param end
   *          The end of the interval.
   * @return A List, possibly empty, of the Measurements made by the sensor in
   *         the time interval.
   */
  public abstract List<Measurement> getMeasurements(Sensor sensor, Date start, Date end);

  /**
   * @return the measurementType
   */
  public String getMeasurementType() {
    return measurementType;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the value for the given sensor at the given time for the given
   * type. This is most likely an interpolated value.
   * 
   * @param sensor
   *          The Sensor.
   * @param timestamp
   *          The time of the measurement.
   * @return The value for the given sensor at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   */
  public abstract Double getValue(Sensor sensor, Date timestamp) throws NoMeasurementException;

  /**
   * Returns the value measured by the given Sensor from start to end for the
   * given type by subtracting the start value from the end value.
   * 
   * @param sensor
   *          The Sensor making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return The value measured by the Sensor.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   */
  public abstract Double getValue(Sensor sensor, Date start, Date end)
      throws NoMeasurementException;

  /**
   * Returns the value measured by the given Sensor from start to end for the
   * given type by subtracting the start value from the end value.
   * 
   * @param sensor
   *          The Sensor making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The value measured by the Sensor.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   * @throws MeasurementGapException
   *           if the gap around the start or end is larger than gapSeconds.
   */
  public abstract Double getValue(Sensor sensor, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException;

  /**
   * Returns the value for the given sensor at the given time for the given
   * type. This is most likely an interpolated value.
   * 
   * @param sensor
   *          The Sensor.
   * @param timestamp
   *          The time of the measurement.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The counter value for the given sensor at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   * @throws MeasurementGapException
   *           if the gap around the timestamp is larger than gapSeconds.
   */
  public abstract Double getValue(Sensor sensor, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException;

  /**
   * @param meas
   *          The measurement to store.
   * @throws MeasurementTypeException
   *           if the measurement's type doesn't match the WattDepository's
   *           measurementType.
   */
  public abstract void putMeasurement(Measurement meas) throws MeasurementTypeException;
}
