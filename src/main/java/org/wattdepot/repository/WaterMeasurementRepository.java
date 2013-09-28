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
import org.wattdepot.datamodel.WaterMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.server.Server;

/**
 * Repository for storing WaterMeasurement.
 * 
 * @author Cam Moore
 */
public class WaterMeasurementRepository {
  /**
   * Key for setting the EnergyMeasurement's unit.
   */
  public static final String WATER_UNIT_KEY = "wattdepot.water.unit";

  private static String unit = "Gallon";

  static {
    Map<String, String> systemProps = System.getenv();
    for (Map.Entry<String, String> prop : systemProps.entrySet()) {
      if (prop.getKey().equals(WATER_UNIT_KEY)) {
        WaterMeasurementRepository.unit = prop.getValue();
      }
    }
  }

  /**
   * Hide the default constructor.
   */
  public WaterMeasurementRepository() {

  }

  /**
   * Returns a List of the WaterMeasurements for the given meter for the given
   * time interval.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return A List, possibly empty, of the WaterMeasurements made by the given
   *         meter in the time interval.
   */
  public List<WaterMeasurement> getMeasurements(Meter meter, Date start, Date end) {
    ArrayList<WaterMeasurement> ret = new ArrayList<WaterMeasurement>();
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<WaterMeasurement> result = entityManager
        .createQuery("FROM WaterMeasurement WHERE timestamp >= :start AND timestamp <= :end",
            WaterMeasurement.class).setParameter("start", start).setParameter("end", end)
        .getResultList();
    entityManager.getTransaction().commit();
    for (WaterMeasurement e : result) {
      if (e.getMeter().equals(meter)) {
        ret.add(e);
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
   * @return The counter value for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   */
  private Double getValue(Meter meter, Date timestamp) throws NoMeasurementException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<WaterMeasurement> result = entityManager
        .createQuery("FROM WaterMeasurement WHERE timestamp = :time", WaterMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (WaterMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          return p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<WaterMeasurement> before = entityManager
          .createQuery("FROM WaterMeasurement WHERE timestamp <= :time", WaterMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      List<WaterMeasurement> after = entityManager
          .createQuery("FROM WaterMeasurement WHERE timestamp >= :time", WaterMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      WaterMeasurement justBefore = null;
      for (WaterMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      WaterMeasurement justAfter = null;
      for (WaterMeasurement p : after) {
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
    List<WaterMeasurement> result = entityManager
        .createQuery("FROM WaterMeasurement WHERE timestamp = :time", WaterMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (WaterMeasurement p : result) {
        if (p.getMeter().equals(meter)) {
          return p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<WaterMeasurement> before = entityManager
          .createQuery("FROM WaterMeasurement WHERE timestamp <= :time", WaterMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      List<WaterMeasurement> after = entityManager
          .createQuery("FROM WaterMeasurement WHERE timestamp >= :time", WaterMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      WaterMeasurement justBefore = null;
      for (WaterMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      WaterMeasurement justAfter = null;
      for (WaterMeasurement p : after) {
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
   * Returns the Water measured by the given Meter from start to end.
   * 
   * @param meter
   *          The Meter making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return The total Water measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   */
  public Double getWater(Meter meter, Date start, Date end) throws NoMeasurementException {
    Double endVal = getValue(meter, end);
    Double startVal = getValue(meter, start);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
    return null;
  }

  /**
   * Returns the Water measured by the given Meter from start to end.
   * 
   * @param meter
   *          The Meter making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The total Water measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   * @throws MeasurementGapException
   *           if the gap around the start or end is larger than gapSeconds.
   */
  public Double getWater(Meter meter, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    Double endVal = getValue(meter, end, gapSeconds);
    Double startVal = getValue(meter, start, gapSeconds);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
    return null;
  }

  /**
   * Stores the given WaterMeasurment. This method will store WaterMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void putMeasurement(WaterMeasurement meas) {
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

  /**
   * @return the unit of measurement for this repository.
   */
  public String getUnit() {
    return unit;
  }

}
