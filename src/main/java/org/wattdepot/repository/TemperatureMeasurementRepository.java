/**
 * 
 */
package org.wattdepot.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.TemperatureMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.server.Server;

/**
 * Repository for storing TemperatureMeasurement.
 * 
 * @author Cam Moore
 */
public class TemperatureMeasurementRepository {
  /**
   * Key for setting the TemperatureMeasurement's unit.
   */
  public static final String TEMPERATURE_UNIT_KEY = "wattdepot.temperature.unit";

  private static String unit = "Degree Fahrenheit";

  static {
    Map<String, String> systemProps = System.getenv();
    for (Map.Entry<String, String> prop : systemProps.entrySet()) {
      if (prop.getKey().equals(TEMPERATURE_UNIT_KEY)) {
        TemperatureMeasurementRepository.unit = prop.getValue();
      }
    }
  }

  /**
   * Default constructor.
   */
  public TemperatureMeasurementRepository() {

  }

  /**
   * @return The temperature units.
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Returns the temperature measured by the given meter at the given time. If
   * there isn't a measurement at the specified time the temperature is
   * interpolated.
   * 
   * @param meter
   *          The meter making the measurement.
   * @param timestamp
   *          The time of the measurement.
   * @return The temperature as a Double.
   * @throws NoMeasurementException
   *           if there aren't any measurements around the timestamp.
   */
  public Double getTemperature(Meter meter, Date timestamp) throws NoMeasurementException {
    return getValue(meter, timestamp);
  }

  /**
   * Returns the temperature measured by the given meter at the given time. If
   * there isn't a measurement at the specified time the temperature is
   * interpolated.
   * 
   * @param meter
   *          The meter making the measurement.
   * @param timestamp
   *          The time of the measurement.
   * @param gapSeconds
   *          The largest gap between measurements around timestamp.
   * @return The temperature as a Double.
   * @throws NoMeasurementException
   *           if there aren't any measurements around the timestamp.
   * @throws MeasurementGapException
   *           if the gap around the timestamp is larger than gapSeconds.
   */
  public Double getTemperature(Meter meter, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    return getValue(meter, timestamp, gapSeconds);
  }

  /**
   * Returns a List of TemperatureMeasurements that occurred between start and
   * end times.
   * 
   * @param meter
   *          The meter that took the measurements.
   * @param start
   *          The start Date.
   * @param end
   *          The end Date.
   * @return A List of TemperatureMeasurments.
   */
  public List<TemperatureMeasurement> getMeasurements(Meter meter, Date start, Date end) {
    ArrayList<TemperatureMeasurement> ret = new ArrayList<TemperatureMeasurement>();
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<TemperatureMeasurement> result = entityManager
        .createQuery("FROM TemperatureMeasurement WHERE timestamp >= :start AND timestamp <= :end",
            TemperatureMeasurement.class).setParameter("start", start).setParameter("end", end)
        .getResultList();
    entityManager.getTransaction().commit();
    for (TemperatureMeasurement t : result) {
      if (t.getMeter().equals(meter)) {
        ret.add(t);
      }
    }
    return ret;
  }

  /**
   * Stores the given TemperatureMeasurment. This method will store
   * TemperatureMeasurments that don't come from the same Meter.
   * 
   * @param meas
   *          The TemperatureMeasurement to store.
   */
  public void putTemperatureMeasurement(TemperatureMeasurement meas) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(meas);
    entityManager.persist(meas.getMeter());
    for (Property p : meas.getMeter().getProperties()) {
      entityManager.persist(p);
    }
    entityManager.persist(meas.getMeter().getLocation());
    entityManager.persist(meas.getMeter().getModel());
    for (Property p : meas.getProperties()) {
      entityManager.persist(p);
    }
    entityManager.getTransaction().commit();
  }

  /**
   * Returns the counter value for the given meter at the given time. This is
   * most likely an interpolated value.
   * 
   * @param meter
   *          The Meter.
   * @param timestamp
   *          The time of the measurement.
   * @return The counter value for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there aren't any measurements around the timestamp.
   */
  private Double getValue(Meter meter, Date timestamp) throws NoMeasurementException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<TemperatureMeasurement> result = entityManager
        .createQuery("FROM TemperatureMeasurement WHERE timestamp = :time",
            TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (TemperatureMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<TemperatureMeasurement> before = entityManager
          .createQuery("FROM TemperatureMeasurement WHERE timestamp <= :time",
              TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
      List<TemperatureMeasurement> after = entityManager
          .createQuery("FROM TemperatureMeasurement WHERE timestamp >= :time",
              TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      TemperatureMeasurement justBefore = null;
      for (TemperatureMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      TemperatureMeasurement justAfter = null;
      for (TemperatureMeasurement p : after) {
        if (p.getMeter().equals(meter)) {
          if (justAfter == null) {
            justAfter = p;
          }
          else if (p.getTimestamp().compareTo(justAfter.getTimestamp()) < 0) {
            justAfter = p;
          }
        }
      }
      if (justBefore != null && justAfter != null) {
        Double val1 = justBefore.getValue();
        Double val2 = justAfter.getValue();
        Double deltaV = val2 - val1;
        Long t1 = justBefore.getTimestamp().getTime();
        Long t2 = justAfter.getTimestamp().getTime();
        Long deltaT = t2 - t1;
        Long t3 = timestamp.getTime();
        Long toTimestamp = t3 - t1;
        Double slope = deltaV / deltaT;
        ret = val1 + (slope * toTimestamp);
      }
      else if (justBefore == null && justAfter == null) {
        throw new NoMeasurementException("Cannot find measurements before or after " + timestamp);
      }
      else if (justBefore == null) {
        throw new NoMeasurementException("Cannot find measurement before " + timestamp);
      }
      else if (justAfter == null) {
        throw new NoMeasurementException("Cannot find measurement after " + timestamp);
      }
    }
    return ret;
  }

  /**
   * Returns the counter value for the given meter at the given time. This is
   * most likely an interpolated value.
   * 
   * @param meter
   *          The Meter.
   * @param timestamp
   *          The time of the measurement.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The counter value for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   * @throws MeasurementGapException
   *           if the gap around the timestamp is larger than gapSeconds.
   */
  private Double getValue(Meter meter, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<TemperatureMeasurement> result = entityManager
        .createQuery("FROM TemperatureMeasurement WHERE timestamp = :time",
            TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (TemperatureMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<TemperatureMeasurement> before = entityManager
          .createQuery("FROM TemperatureMeasurement WHERE timestamp <= :time",
              TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
      List<TemperatureMeasurement> after = entityManager
          .createQuery("FROM TemperatureMeasurement WHERE timestamp >= :time",
              TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      TemperatureMeasurement justBefore = null;
      for (TemperatureMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      TemperatureMeasurement justAfter = null;
      for (TemperatureMeasurement p : after) {
        if (p.getMeter().equals(meter)) {
          if (justAfter == null) {
            justAfter = p;
          }
          else if (p.getTimestamp().compareTo(justAfter.getTimestamp()) < 0) {
            justAfter = p;
          }
        }
      }
      if (justBefore != null && justAfter != null) {
        Double val1 = justBefore.getValue();
        Double val2 = justAfter.getValue();
        Double deltaV = val2 - val1;
        Long t1 = justBefore.getTimestamp().getTime();
        Long t2 = justAfter.getTimestamp().getTime();
        Long deltaT = t2 - t1;
        if ((deltaT / 1000) > gapSeconds) {
          throw new MeasurementGapException("Gap of " + (deltaT / 1000) + "s is longer than "
              + gapSeconds);
        }
        Long t3 = timestamp.getTime();
        Long toTimestamp = t3 - t1;
        Double slope = deltaV / deltaT;
        ret = val1 + (slope * toTimestamp);
      }
      else if (justBefore == null && justAfter == null) {
        throw new NoMeasurementException("Cannot find measurements before or after " + timestamp);
      }
      else if (justBefore == null) {
        throw new NoMeasurementException("Cannot find measurement before " + timestamp);
      }
      else if (justAfter == null) {
        throw new NoMeasurementException("Cannot find measurement after " + timestamp);
      }
    }
    return ret;
  }
}
