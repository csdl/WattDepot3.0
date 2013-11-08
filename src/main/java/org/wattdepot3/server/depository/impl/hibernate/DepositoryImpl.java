/**
 * DepositoryImpl.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.exception.MeasurementGapException;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.NoMeasurementException;

/**
 * DepositoryImpl - Implementation of Depository that stores the Measurements in
 * a Hibernate database.
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryImpl extends Depository {

  /**
   * Creates a DepositoryImpl from a Depository.
   * 
   * @param clone
   *          The Depository to clone.
   */
  public DepositoryImpl(Depository clone) {
    super(clone.getName(), clone.getMeasurementType(), clone.getOwner());
  }

  /**
   * @param sensor
   *          The Sensor.
   * @param start
   *          The start of the interval.
   * @param end
   *          The end of the interval.
   * @return A list of the measurements in the interval.
   */
  public List<Measurement> getMeasurements(Sensor sensor, Timestamp start, Timestamp end) {
    return null;
  }

  
  /**
   * @param meas
   *          The measurement to store.
   * @throws MeasurementTypeException
   *           if the type of the measurement doesn't match the Depository
   *           measurement type.
   */
  @Override
  public void putMeasurement(Measurement meas) throws MeasurementTypeException {
    if (!meas.getMeasurementType().equals(getMeasurementType())) {
      throw new MeasurementTypeException("Measurement's type " + meas.getMeasurementType()
          + " does not match " + getMeasurementType());
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();    
    MeasurementImpl mImpl = new MeasurementImpl(meas);
    mImpl.setDepository(this);
    session.saveOrUpdate(mImpl);
    session.getTransaction().commit();
    session.close();    
  }

  /**
   * @param sensor
   *          The Sensor making the measurements.
   * @param timestamp
   *          The time of the value.
   * @return The Value 'measured' at the given time, most likely an interpolated
   *         value.
   * @throws NoMeasurementException
   *           If there aren't any measurements around the time.
   */
  public Double getValue(Sensor sensor, Date timestamp) throws NoMeasurementException {
    return null;
  }

  /**
   * @param sensor
   *          The Sensor making the measurements.
   * @param start
   *          The start of the period.
   * @param end
   *          The end of the period.
   * @return The value measured the difference between the end value and the
   *         start value.
   * @throws NoMeasurementException
   *           if there are no measurements around the start or end time.
   */
  public Double getValue(Sensor sensor, Date start, Date end) throws NoMeasurementException {
    return null;
  }

  /**
   * @param sensor
   *          The Sensor making the measurements.
   * @param start
   *          The start of the interval.
   * @param end
   *          The end of the interval
   * @param gapSeconds
   *          The maximum number of seconds that measurements need to be within
   *          the start and end.
   * @return The value measured the difference between the end value and the
   *         start value.
   * @throws NoMeasurementException
   *           if there are no measurements around the start or end time.
   * @throws MeasurementGapException
   *           if the measurements around start or end are too far apart.
   */
  public Double getValue(Sensor sensor, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    return null;
  }

  /**
   * @param sensor
   *          The Sensor making the measurements.
   * @param timestamp
   *          The time of the value.
   * @param gapSeconds
   *          The maximum number of seconds that measurements need to be within
   *          the start and end.
   * @return The Value 'measured' at the given time, most likely an interpolated
   *         value.
   * @throws NoMeasurementException
   *           If there aren't any measurements around the time.
   * @throws MeasurementGapException
   *           if the measurements around timestamp are too far apart.
   */
  public Double getValue(Sensor sensor, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    return null;
  }

}
