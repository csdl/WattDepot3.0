/**
 * 
 */
package org.wattdepot.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.PowerMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.datamodel.TemperatureMeasurement;
import org.wattdepot.datamodel.WaterMeasurement;
import org.wattdepot.server.Server;

/**
 * Repository that stores measurements that WattDepot understands.
 * 
 * @author Cam Moore
 */
public class WattDepotRepository {
  private static WattDepotRepository instance;

  /**
   * @return The singleton WattDepotRepository instance.
   */
  public static WattDepotRepository getInstance() {
    if (instance == null) {
      instance = new WattDepotRepository();
    }
    return instance;
  }

  /**
   * Hide the default constructor.
   */
  private WattDepotRepository() {

  }

  /**
   * Returns the EnergyMeasurement at the given time for the given meter or null
   * if the timestamp is before the first measurement or after the last
   * measurement.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param timestamp
   *          The time of the measurement.
   * @return A EnergyMeasurment, possibly interpolated, or null.
   */
  public EnergyMeasurement getEnergyMeasurement(Meter meter, Date timestamp) {
    return EnergyMeasurementRepository.getInstance(meter).getEnergyMeasurement(timestamp);
  }

//  public EnergyMeasurement getEnergyMeasurement(VirtualMeter meter, Date timestamp) {
//    Double val = 0.0;
//    for (Meter m : meter.getAllMeters()) {
//      EnergyMeasurement temp = EnergyMeasurementRepository.getInstance(m).getEnergyMeasurement(
//          timestamp);
//      if (temp == null) {
//        return null;
//      }
//      else if (val == null) {
//        val = temp.getValue();
//      }
//      else {
//        val += temp.getValue();
//      }
//    }
//    return null;
//  }

  /**
   * @return A List of the defined Meters in the WattDepotRepository.
   */
  public List<Meter> getMeters() {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<Meter> meters = entityManager.createQuery("FROM Meter", Meter.class).getResultList();
    entityManager.getTransaction().commit();
    return meters;
  }

  /**
   * Returns the PowerMeasurement at the given time for the given meter or null
   * if the timestamp is before the first measurement or after the last
   * measurement.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param timestamp
   *          The time of the measurement.
   * @return A PowerMeasurment, possibly interpolated, or null.
   */
  public PowerMeasurement getPowerMeasurement(Meter meter, Date timestamp) {
    return PowerMeasurementRepository.getInstance(meter).getPowerMeasurement(timestamp);
  }

  /**
   * Returns the TemperatureMeasurement at the given time for the given meter or
   * null if the timestamp is before the first measurement or after the last
   * measurement.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param timestamp
   *          The time of the measurement.
   * @return A TemperatureMeasurment, possibly interpolated, or null.
   */
  public TemperatureMeasurement getTemperatureMeasurement(Meter meter, Date timestamp) {
    return TemperatureMeasurementRepository.getInstance(meter).getTemperatureMeasurement(timestamp);
  }

  /**
   * Returns the WaterMeasurement at the given time for the given meter or null
   * if the timestamp is before the first measurement or after the last
   * measurement.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param timestamp
   *          The time of the measurement.
   * @return A WaterMeasurment, possibly interpolated, or null.
   */
  public WaterMeasurement getWaterMeasurement(Meter meter, Date timestamp) {
    return WaterMeasurementRepository.getInstance(meter).getWaterMeasurement(timestamp);
  }

  /**
   * Stores the given EnergyMeasurement in the repository.
   * 
   * @param meas
   *          The EnergyMeasurement.
   */
  public void storeEnergyMeasurement(EnergyMeasurement meas) {
    EnergyMeasurementRepository.getInstance(meas.getMeter()).storeEnergyMeasurement(meas);
  }

  /**
   * Stores the given Meter in the WattDepotRepository.
   * 
   * @param meter
   *          The Meter.
   */
  public void storeMeter(Meter meter) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(meter);
    entityManager.persist(meter.getLocation());
    entityManager.persist(meter.getModel());
    for (Property p : meter.getProperties()) {
      entityManager.persist(p);
    }
    entityManager.getTransaction().commit();
  }

  /**
   * Stores the given PowerMeasurement in the repository.
   * 
   * @param meas
   *          The PowerMeasurement.
   */
  public void storePowerMeasurement(PowerMeasurement meas) {
    PowerMeasurementRepository.getInstance(meas.getMeter()).storePowerMeasurement(meas);
  }

  /**
   * Stores the given TemperatureMeasurement in the repository.
   * 
   * @param meas
   *          The TemperatureMeasurement.
   */
  public void storeTemperatureMeasurement(TemperatureMeasurement meas) {
    TemperatureMeasurementRepository.getInstance(meas.getMeter()).storeTemperatureMeasurement(meas);
  }

  /**
   * Stores the given WaterMeasurement in the repository.
   * 
   * @param meas
   *          The WaterMeasurement.
   */
  public void storeWaterMeasurement(WaterMeasurement meas) {
    WaterMeasurementRepository.getInstance(meas.getMeter()).storeWaterMeasurement(meas);
  }

}