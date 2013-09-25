/**
 * 
 */
package org.wattdepot.repository;

import java.util.Date;
import java.util.HashMap;
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
  private static Map<Meter, TemperatureMeasurementRepository> instances = new HashMap<Meter, TemperatureMeasurementRepository>();

  /**
   * @param meter The Meter.
   * @return The TemperatureMeasurementRepository for the given Meter.
   */
  public static TemperatureMeasurementRepository getInstance(Meter meter) {
    if (instances.get(meter) == null) {
      instances.put(meter, new TemperatureMeasurementRepository(meter));
    }
    return instances.get(meter);
  }

  private Meter meter;

  /**
   * Hide the default constructor.
   */
  private TemperatureMeasurementRepository() {

  }

  /**
   * Creates a TemperatureMeasurementRepository for the given meter.
   * @param meter The Meter.
   */
  private TemperatureMeasurementRepository(Meter meter) {
    this.meter = meter;
  }

  /**
   * Returns the TemperatureMeasurement at the given time or null if the
   * timestamp is before the first measurement or after the last measurement.
   * 
   * @param timestamp
   *          The time of the measurement.
   * @return A PowerMeasurment, possibly interpolated, or null.
   */
  public TemperatureMeasurement getTemperatureMeasurement(Date timestamp) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<TemperatureMeasurement> result = entityManager
        .createQuery("FROM TemperatureMeasurement WHERE timestamp = :time",
            TemperatureMeasurement.class).setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (TemperatureMeasurement p : result) {
        if (p.getMeter().equals(this.meter)) {
          return p;
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
        Double interpolatedValue = val1 + (slope * toTimestamp);
        TemperatureMeasurement ret = new TemperatureMeasurement(timestamp, interpolatedValue,
            meter, true);
        return ret;
      }
    }
    return null;
  }

  /**
   * Stores the given TemperatureMeasurment. This method will store
   * TemperatureMeasurments that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void storeTemperatureMeasurement(TemperatureMeasurement meas) {
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
}
