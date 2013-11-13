/**
 * DepositoryImpl.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.UserGroup;
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
   * Default constructor.
   */
  public DepositoryImpl() {
    super();
  }

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
   * @param name
   *          The name of the depository.
   * @param measurementType
   *          The type of the measurement.
   * @param owner
   *          The owner.
   */
  public DepositoryImpl(String name, String measurementType, UserGroup owner) {
    super(name, measurementType, owner);
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
  @Override
  public List<Measurement> getMeasurements(Sensor sensor, Date start, Date end) {
    List<Measurement> ret = new ArrayList<Measurement>();
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("unchecked")
    List<MeasurementImpl> measurements = (List<MeasurementImpl>) session
        .createQuery(
            "FROM MeasurementImpl WHERE date >= :start AND date <= :end AND depository = :depository")
        .setParameter("start", start).setParameter("end", end).setParameter("depository", this)
        .list();
    for (MeasurementImpl meas : measurements) {
      if (meas.getSensor().equals(sensor)) {
        ret.add(meas);
      }
    }
    session.getTransaction().commit();
    session.close();
    return ret;
  }

  /**
   * @param meas
   *          The measurement to store.
   * @throws MeasurementTypeException
   *           if the type of the measurement doesn't match the Depository
   *           measurement type.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void putMeasurement(Measurement meas) throws MeasurementTypeException {
    if (!meas.getMeasurementType().equals(getMeasurementType())) {
      throw new MeasurementTypeException("Measurement's type " + meas.getMeasurementType()
          + " does not match " + getMeasurementType());
    }

    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    // check the sensor
    boolean haveSensor = false;
    for (Sensor s : (List<Sensor>) session.createQuery("from Sensor").list()) {
      if (s.getId().equals(meas.getSensor().getId())) {
        haveSensor = true;
      }
    }
    if (!haveSensor) {
      // ensure the sensor has an owner
      Sensor measSensor = meas.getSensor();
      if (measSensor.getOwner() == null) {
        measSensor.setOwner(getOwner());
      }
      session.saveOrUpdate(meas.getSensor());
    }
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
    Double ret = null;
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();

    @SuppressWarnings("unchecked")
    List<MeasurementImpl> result = (List<MeasurementImpl>) session
        .createQuery(
            "FROM MeasurementImpl WHERE date = :time AND measurementType = :measType"
                + " AND depository = :name").setParameter("time", timestamp)
        .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
    if (result.size() > 0) {
      for (MeasurementImpl meas : result) {
        if (meas.getSensor().equals(sensor)) {
          ret = meas.getValue();
        }
      }
    }
    else {
      // need to get the stradle
      @SuppressWarnings("unchecked")
      List<MeasurementImpl> before = (List<MeasurementImpl>) session
          .createQuery(
              "FROM MeasurementImpl WHERE date <= :time AND measurementType = :measType"
                  + " AND depository = :name").setParameter("time", timestamp)
          .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
      @SuppressWarnings("unchecked")
      List<MeasurementImpl> after = (List<MeasurementImpl>) session
          .createQuery(
              "FROM MeasurementImpl WHERE date >= :time AND measurementType = :measType"
                  + " AND depository = :name").setParameter("time", timestamp)
          .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
      MeasurementImpl justBefore = null;
      for (MeasurementImpl b : before) {
        if (b.getSensor().equals(sensor)) {
          if (justBefore == null) {
            justBefore = b;
          }
          else if (b.getDate().compareTo(justBefore.getDate()) > 0) {
            justBefore = b;
          }
        }
        MeasurementImpl justAfter = null;
        for (MeasurementImpl a : after) {
          if (a.getSensor().equals(sensor)) {
            if (justAfter == null) {
              justAfter = a;
            }
            else if (a.getDate().compareTo(justBefore.getDate()) > 0) {
              justAfter = a;
            }
          }
          if (justBefore != null && justAfter != null) {
            Double val1 = justBefore.getValue();
            Double val2 = justAfter.getValue();
            Double deltaV = val2 - val1;
            Long t1 = justBefore.getDate().getTime();
            Long t2 = justAfter.getDate().getTime();
            Long deltaT = t2 - t1;
            Long t3 = timestamp.getTime();
            Long toDate = t3 - t1;
            Double slope = deltaV / deltaT;
            ret = val1 + (slope * toDate);
          }
          else if (justBefore == null && justAfter == null) {
            throw new NoMeasurementException("Cannot find measurements before or after "
                + timestamp);
          }
          else if (justBefore == null) {
            throw new NoMeasurementException("Cannot find measurement before " + timestamp);
          }
          else if (justAfter == null) {
            throw new NoMeasurementException("Cannot find measurement after " + timestamp);
          }
        }
      }
    }
    session.getTransaction().commit();
    session.close();
    return ret;
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
    Double endVal = getValue(sensor, end);
    Double startVal = getValue(sensor, start);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
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
    Double endVal = getValue(sensor, end, gapSeconds);
    Double startVal = getValue(sensor, start, gapSeconds);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
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
    Double ret = null;
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();

    @SuppressWarnings("unchecked")
    List<MeasurementImpl> result = (List<MeasurementImpl>) session
        .createQuery(
            "FROM MeasurementImpl WHERE date = :time AND measurementType = :measType"
                + " AND depository = :name").setParameter("time", timestamp)
        .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
    if (result.size() > 0) {
      for (MeasurementImpl meas : result) {
        if (meas.getSensor().equals(sensor)) {
          ret = meas.getValue();
        }
      }
    }
    else {
      // need to get the stradle
      @SuppressWarnings("unchecked")
      List<MeasurementImpl> before = (List<MeasurementImpl>) session
          .createQuery(
              "FROM MeasurementImpl WHERE date <= :time AND measurementType = :measType"
                  + " AND depository = :name").setParameter("time", timestamp)
          .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
      @SuppressWarnings("unchecked")
      List<MeasurementImpl> after = (List<MeasurementImpl>) session
          .createQuery(
              "FROM MeasurementImpl WHERE date >= :time AND measurementType = :measType"
                  + " AND depository = :name").setParameter("time", timestamp)
          .setParameter("measType", getMeasurementType()).setParameter("name", this).list();
      MeasurementImpl justBefore = null;
      for (MeasurementImpl b : before) {
        if (b.getSensor().equals(sensor)) {
          if (justBefore == null) {
            justBefore = b;
          }
          else if (b.getDate().compareTo(justBefore.getDate()) > 0) {
            justBefore = b;
          }
        }
        MeasurementImpl justAfter = null;
        for (MeasurementImpl a : after) {
          if (a.getSensor().equals(sensor)) {
            if (justAfter == null) {
              justAfter = a;
            }
            else if (a.getDate().compareTo(justBefore.getDate()) > 0) {
              justAfter = a;
            }
          }
          if (justBefore != null && justAfter != null) {
            Double val1 = justBefore.getValue();
            Double val2 = justAfter.getValue();
            Double deltaV = val2 - val1;
            Long t1 = justBefore.getDate().getTime();
            Long t2 = justAfter.getDate().getTime();
            Long deltaT = t2 - t1;
            if ((deltaT / 1000) > gapSeconds) {
              throw new MeasurementGapException("Gap of " + (deltaT / 1000) + "s is longer than "
                  + gapSeconds);
            }
            Long t3 = timestamp.getTime();
            Long toDate = t3 - t1;
            Double slope = deltaV / deltaT;
            ret = val1 + (slope * toDate);
          }
          else if (justBefore == null && justAfter == null) {
            throw new NoMeasurementException("Cannot find measurements before or after "
                + timestamp);
          }
          else if (justBefore == null) {
            throw new NoMeasurementException("Cannot find measurement before " + timestamp);
          }
          else if (justAfter == null) {
            throw new NoMeasurementException("Cannot find measurement after " + timestamp);
          }
        }
      }
    }
    session.getTransaction().commit();
    session.close();
    return ret;
  }

}
