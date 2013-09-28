package org.wattdepot.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.PowerMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.server.Server;

/**
 * Repository for storing PowerMeasurements.
 * 
 * @author Cam Moore
 */
public class PowerMeasurementRepository {
  /**
   * Key for setting the PowerMeasurement's unit.
   */
  public static final String POWER_UNIT_KEY = "wattdepot.power.unit";

  private static String unit = "Watt";

  static {
    Map<String, String> systemProps = System.getenv();
    for (Map.Entry<String, String> prop : systemProps.entrySet()) {
      if (prop.getKey().equals(POWER_UNIT_KEY)) {
        PowerMeasurementRepository.unit = prop.getValue();
      }
    }
  }

  /**
   * Default Constructor.
   */
  public PowerMeasurementRepository() {

  }

  /**
   * Returns a List of PowerMeasurements that occurred between start and end
   * times.
   * 
   * @param meter
   *          The meter that took the measurements.
   * @param start
   *          The start Date.
   * @param end
   *          The end Date.
   * @return A List of PowerMeasurments.
   */
  public List<PowerMeasurement> getMeasurements(Meter meter, Date start, Date end) {
    ArrayList<PowerMeasurement> ret = new ArrayList<PowerMeasurement>();
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<PowerMeasurement> result = entityManager
        .createQuery("FROM PowerMeasurement WHERE timestamp >= :start AND timestamp <= :end",
            PowerMeasurement.class).setParameter("start", start).setParameter("end", end)
        .getResultList();
    entityManager.getTransaction().commit();
    for (PowerMeasurement e : result) {
      if (e.getMeter().equals(meter)) {
        ret.add(e);
      }
    }
    return ret;

  }

  /**
   * Get the power for the given meter at the given time.
   * 
   * @param meter
   *          The meter that took the measurements.
   * @param timestamp
   *          The time for the Power.
   * @return The power as a Double.
   * @throws NoMeasurementException
   *           if there aren't any measurements around the time.
   */
  public Double getPower(Meter meter, Date timestamp) throws NoMeasurementException {
    return getValue(meter, timestamp);
  }

  /**
   * @param meter
   *          The meter
   * @param timestamp
   *          The time of the measurement.
   * @param gapSeconds
   *          The largest gap between measurements around timestamp.
   * @return The power reading for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there aren't measurements around timestamp.
   * @throws MeasurementGapException
   *           if the gap around the timestamp is larger than gapSeconds.
   */
  public Double getPower(Meter meter, Date timestamp, Long gapSeconds)
      throws MeasurementGapException, NoMeasurementException {
    return getValue(meter, timestamp, gapSeconds);
  }

  /**
   * @return The power units as a string.
   */
  public String getUnit() {
    return unit;
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
   *           if there is no measurements around the timestamp.
   */
  private Double getValue(Meter meter, Date timestamp) throws NoMeasurementException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<PowerMeasurement> result = entityManager
        .createQuery("FROM PowerMeasurement WHERE timestamp = :time", PowerMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (PowerMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<PowerMeasurement> before = entityManager
          .createQuery("FROM PowerMeasurement WHERE timestamp <= :time", PowerMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      List<PowerMeasurement> after = entityManager
          .createQuery("FROM PowerMeasurement WHERE timestamp >= :time", PowerMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      PowerMeasurement justBefore = null;
      for (PowerMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      PowerMeasurement justAfter = null;
      for (PowerMeasurement p : after) {
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
    List<PowerMeasurement> result = entityManager
        .createQuery("FROM PowerMeasurement WHERE timestamp = :time", PowerMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (PowerMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<PowerMeasurement> before = entityManager
          .createQuery("FROM PowerMeasurement WHERE timestamp <= :time", PowerMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      List<PowerMeasurement> after = entityManager
          .createQuery("FROM PowerMeasurement WHERE timestamp >= :time", PowerMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      PowerMeasurement justBefore = null;
      for (PowerMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      PowerMeasurement justAfter = null;
      for (PowerMeasurement p : after) {
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

  /**
   * Stores the given PowerMeasurment. This method will store PowerMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void putMeasurement(PowerMeasurement meas) {
    if (getMeasurements(meas.getMeter(), meas.getTimestamp(), meas.getTimestamp()).size() == 0) {
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
      entityManager.flush();
      entityManager.getTransaction().commit();
    }
  }

}
