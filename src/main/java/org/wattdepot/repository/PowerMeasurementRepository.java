package org.wattdepot.repository;

import java.util.Date;
import java.util.HashMap;
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
  private static Map<Meter, PowerMeasurementRepository> instances = new HashMap<Meter, PowerMeasurementRepository>();

  private Meter meter;

  /**
   * Hide the default constructor.
   */
  private PowerMeasurementRepository() {

  }

  /**
   * Hides the Constructor.
   * @param meter The Meter.
   */
  private PowerMeasurementRepository(Meter meter) {
    this.meter = meter;
  }

  /**
   * Returns the PowerMeasurementRepository for the given Meter.
   * @param meter The Meter.
   * @return The PowerMeasurementRepository for the given Meter.
   */
  public static PowerMeasurementRepository getInstance(Meter meter) {
    if (instances.get(meter) == null) {
      instances.put(meter, new PowerMeasurementRepository(meter));
    }
    return instances.get(meter);
  }

  /**
   * Stores the given PowerMeasurment. This method will store PowerMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void storePowerMeasurement(PowerMeasurement meas) {
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
   * Returns the PowerMeasurement at the given time or null if the timestamp is
   * before the first measurement or after the last measurement.
   * 
   * @param timestamp
   *          The time of the measurement.
   * @return A PowerMeasurment, possibly interpolated, or null.
   */
  public PowerMeasurement getPowerMeasurement(Date timestamp) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<PowerMeasurement> result = entityManager
        .createQuery("FROM PowerMeasurement WHERE timestamp = :time", PowerMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (PowerMeasurement p : result) {
        if (p.getMeter().equals(this.meter)) {
          return p;
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
        Double interpolatedValue = val1 + (slope * toTimestamp);
        PowerMeasurement ret = new PowerMeasurement(timestamp, interpolatedValue, meter, true);
        return ret;
      }
    }
    return null;
  }
}
