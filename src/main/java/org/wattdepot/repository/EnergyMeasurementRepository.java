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
import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.server.Server;

/**
 * Repository for storing EnergyMeasurement.
 * 
 * @author Cam Moore
 */
public class EnergyMeasurementRepository {
  private static Map<Meter, EnergyMeasurementRepository> instances = new HashMap<Meter, EnergyMeasurementRepository>();

  /**
   * Returns the EnergyMeasurmentRepository for the given Meter.
   * 
   * @param meter
   *          The Meter.
   * @return The EnergyMeasurementRepository for the given Meter.
   */
  public static EnergyMeasurementRepository getInstance(Meter meter) {
    if (instances.get(meter) == null) {
      instances.put(meter, new EnergyMeasurementRepository(meter));
    }
    return instances.get(meter);
  }

  private Meter meter;

  /**
   * Hide the default constructor.
   */
  private EnergyMeasurementRepository() {

  }

  /**
   * Creates an EnergyMeasurementRepository for the given Meter.
   * 
   * @param meter
   *          The Meter.
   */
  private EnergyMeasurementRepository(Meter meter) {
    this.meter = meter;
  }

  /**
   * Returns the EnergyMeasurement at the given time or null if the timestamp is
   * before the first measurement or after the last measurement.
   * 
   * @param timestamp
   *          The time of the measurement.
   * @return A PowerMeasurment, possibly interpolated, or null.
   */
  public EnergyMeasurement getEnergyMeasurement(Date timestamp) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<EnergyMeasurement> result = entityManager
        .createQuery("FROM EnergyMeasurement WHERE timestamp = :time", EnergyMeasurement.class)
        .setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (EnergyMeasurement p : result) {
        if (p.getMeter().equals(this.meter)) {
          return p;
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<EnergyMeasurement> before = entityManager
          .createQuery("FROM EnergyMeasurement WHERE timestamp <= :time", EnergyMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      List<EnergyMeasurement> after = entityManager
          .createQuery("FROM EnergyMeasurement WHERE timestamp >= :time", EnergyMeasurement.class)
          .setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      EnergyMeasurement justBefore = null;
      for (EnergyMeasurement p : before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      EnergyMeasurement justAfter = null;
      for (EnergyMeasurement p : after) {
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
        EnergyMeasurement ret = new EnergyMeasurement(timestamp, interpolatedValue, meter, true);
        return ret;
      }
    }
    return null;
  }

  /**
   * Stores the given EnergyMeasurment. This method will store EnergyMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void storeEnergyMeasurement(EnergyMeasurement meas) {
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
