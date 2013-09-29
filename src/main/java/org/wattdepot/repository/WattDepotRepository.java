/**
 * WattDepotRepository.java created on Sep 29, 2013 by Cam Moore.
 */
package org.wattdepot.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.wattdepot.datamodel.EnergyMeasurement;
import org.wattdepot.datamodel.IMeasurement;
import org.wattdepot.datamodel.Location;
import org.wattdepot.datamodel.Meter;
import org.wattdepot.datamodel.MeterModel;
import org.wattdepot.datamodel.PowerMeasurement;
import org.wattdepot.datamodel.Property;
import org.wattdepot.datamodel.TemperatureMeasurement;
import org.wattdepot.datamodel.VirtualMeter;
import org.wattdepot.datamodel.WaterMeasurement;
import org.wattdepot.server.Server;

/**
 * WattDepotRepository provides a repository for the data model instances.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotRepository {

  /** Key for setting the energy unit. */
  public static final String ENERGY_UNIT_KEY = "wattdepot.energy.unit";
  /** Key for setting the power unit. */
  public static final String POWER_UNIT_KEY = "wattdepot.power.unit";
  /** Key for setting the temperature unit. */
  public static final String TEMPERATURE_UNIT_KEY = "wattdepot.temperature.unit";
  /** Key for setting the water unit. */
  public static final String WATER_UNIT_KEY = "wattdepot.water.unit";;

  private static final MeasurementType ENERGY = new MeasurementType("EnergyMeasurement",
      EnergyMeasurement.class);
  private static final MeasurementType POWER = new MeasurementType("PowerMeasurement",
      PowerMeasurement.class);
  private static final MeasurementType TEMPERATURE = new MeasurementType("TemperatureMeasurement",
      TemperatureMeasurement.class);
  private static final MeasurementType WATER = new MeasurementType("WaterMeasurement",
      WaterMeasurement.class);

  private static String energyUnit = "Watt Second";
  private static String powerUnit = "Watt";
  private static String temperatureUnit = "Degree Fahrenheit";
  private static String waterUnit = "Gallon";

  static {
    Map<String, String> systemProps = System.getenv();
    for (Map.Entry<String, String> prop : systemProps.entrySet()) {
      switch (prop.getKey()) {
      case WattDepotRepository.ENERGY_UNIT_KEY:
        WattDepotRepository.energyUnit = prop.getValue();
        break;
      case WattDepotRepository.POWER_UNIT_KEY:
        WattDepotRepository.waterUnit = prop.getValue();
        break;
      case WattDepotRepository.TEMPERATURE_UNIT_KEY:
        WattDepotRepository.temperatureUnit = prop.getValue();
        break;
      case WattDepotRepository.WATER_UNIT_KEY:
        WattDepotRepository.waterUnit = prop.getValue();
        break;
      default:
        // ignore the property.
      }
    }
  }

  /**
   * Default constructor.
   */
  public WattDepotRepository() {

  }

  /**
   * Returns the Energy measured by the given Meter from start to end.
   * 
   * @param meter
   *          The Meter making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return The total Energy measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   */
  public Double getEnergy(Meter meter, Date start, Date end) throws NoMeasurementException {
    Double endVal = getValue(meter, end, ENERGY);
    Double startVal = getValue(meter, start, ENERGY);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
    return null;
  }

  /**
   * Returns the sum of Energy measured by the meters in the VirtualMeter from
   * start to end.
   * 
   * @param meter
   *          The VirtualMeter.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return The total Energy measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   */
  public Double getEnergy(VirtualMeter meter, Date start, Date end) throws NoMeasurementException {
    Double ret = new Double(0.0);
    for (Meter m : meter.getAllMeters()) {
      Double val = getEnergy(m, start, end);
      if (val != null) {
        ret += val;
      }
      else {
        return null;
      }
    }
    return ret;
  }

  /**
   * Returns the Energy measured by the given Meter from start to end.
   * 
   * @param meter
   *          The Meter making the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The total Energy measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   * @throws MeasurementGapException
   *           if the gap around the start or end is larger than gapSeconds.
   */
  public Double getEnergy(Meter meter, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    Double endVal = getValue(meter, end, gapSeconds, ENERGY);
    Double startVal = getValue(meter, start, gapSeconds, ENERGY);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
    return null;
  }

  /**
   * Returns the sum of Energy measured by the meters in the VirtualMeter from
   * start to end.
   * 
   * @param meter
   *          The VirtualMeter.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @return The total Energy measured by the Meter.
   * @throws NoMeasurementException
   *           if can't find measurements around start or end dates.
   * @throws MeasurementGapException
   *           if the gap around the start or end is larger than gapSeconds.
   */
  public Double getEnergy(VirtualMeter meter, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    Double ret = new Double(0.0);
    for (Meter m : meter.getAllMeters()) {
      Double val = getEnergy(m, start, end, gapSeconds);
      if (val != null) {
        ret += val;
      }
      else {
        return null;
      }
    }
    return ret;
  }

  /**
   * Returns a List of the EnergyMeasurements for the given meter for the given
   * time interval.
   * 
   * @param meter
   *          The Meter that made the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return A List, possibly empty, of the EnergyMeasurements made by the given
   *         meter in the time interval.
   */
  public List<EnergyMeasurement> getEnergyMeasurements(Meter meter, Date start, Date end) {
    ArrayList<EnergyMeasurement> ret = new ArrayList<EnergyMeasurement>();
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<EnergyMeasurement> result = entityManager
        .createQuery("FROM EnergyMeasurement WHERE timestamp >= :start AND timestamp <= :end",
            EnergyMeasurement.class).setParameter("start", start).setParameter("end", end)
        .getResultList();
    entityManager.getTransaction().commit();
    for (EnergyMeasurement e : result) {
      if (e.getMeter().equals(meter)) {
        ret.add(e);
      }
    }
    return ret;
  }

  /**
   * @return The energy unit as a String.
   */
  public String getEnergyUnit() {
    return energyUnit;
  }

  /**
   * @return A List of the Locations in the repository.
   */
  public List<Location> getLocations() {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<Location> result = entityManager.createQuery("from Location", Location.class)
        .getResultList();
    entityManager.getTransaction().commit();
    return result;
  }

  /**
   * @return A List of the meters in the repository.
   */
  public List<Meter> getMeters() {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<Meter> result = entityManager.createQuery("from Meter", Meter.class).getResultList();
    entityManager.getTransaction().commit();
    return result;
  }

  /**
   * @return A List of the meter models in the repository.
   */
  public List<MeterModel> getMeterModels() {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<MeterModel> result = entityManager.createQuery("from MeterModel", MeterModel.class)
        .getResultList();
    entityManager.getTransaction().commit();
    return result;
  }

  /**
   * @param meter
   *          The Meter.
   * @param timestamp
   *          The time of the measurement.
   * @return The power reading for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there are no measurements around timestamp.
   */
  public Double getPower(Meter meter, Date timestamp) throws NoMeasurementException {
    return getValue(meter, timestamp, POWER);
  }

  /**
   * Returns the sum of Power measured by the meters in the VirtualMeter from
   * start to end.
   * 
   * @param meter
   *          The VirtualMeter.
   * @param timestamp
   *          The time of the measurement.
   * @return The power reading for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there are no measurements around timestamp.
   */
  public Double getPower(VirtualMeter meter, Date timestamp) throws NoMeasurementException {
    Double ret = new Double(0.0);
    for (Meter m : meter.getAllMeters()) {
      Double val = getPower(m, timestamp);
      if (val != null) {
        ret += val;
      }
      else {
        return null;
      }
    }
    return ret;
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
    return getValue(meter, timestamp, gapSeconds, POWER);
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
  public List<PowerMeasurement> getPowerMeasurements(Meter meter, Date start, Date end) {
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
   * @return The power unit as a String.
   */
  public String getPowerUnit() {
    return powerUnit;
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
    return getValue(meter, timestamp, TEMPERATURE);
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
    return getValue(meter, timestamp, gapSeconds, TEMPERATURE);
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
  public List<TemperatureMeasurement> getTemperatureMeasurements(Meter meter, Date start, Date end) {
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
   * @return The temperature unit as a String.
   */
  public String getTemperatureUnit() {
    return temperatureUnit;
  }

  /**
   * Returns the energy counter value for the given meter at the given time.
   * This is most likely an interpolated value.
   * 
   * @param meter
   *          The Meter.
   * @param timestamp
   *          The time of the measurement.
   * @param gapSeconds
   *          The largest gap between measurements allowed.
   * @param type
   *          The measurement type. Should be one of the static final values.
   * @return The counter value for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   * @throws MeasurementGapException
   *           if the gap around the timestamp is larger than gapSeconds.
   */
  @SuppressWarnings("unchecked")
  private Double getValue(Meter meter, Date timestamp, Long gapSeconds, MeasurementType type)
      throws NoMeasurementException, MeasurementGapException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<?> result = entityManager
        .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp = :time",
            type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (IMeasurement p : (List<IMeasurement>) result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<?> before = entityManager
          .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp <= :time",
              type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
      List<?> after = entityManager
          .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp >= :time",
              type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      IMeasurement justBefore = null;
      for (IMeasurement p : (List<IMeasurement>) before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      IMeasurement justAfter = null;
      for (IMeasurement p : (List<IMeasurement>) after) {
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
   * Returns the value for the given meter at the given time. This is most
   * likely an interpolated value.
   * 
   * @param meter
   *          The Meter.
   * @param timestamp
   *          The time of the measurement.
   * @param type
   *          The measurement type. Should be one of the static final values.
   * @return The counter value for the given meter at the given time.
   * @throws NoMeasurementException
   *           if there is no measurements around the timestamp.
   */
  @SuppressWarnings("unchecked")
  private Double getValue(Meter meter, Date timestamp, MeasurementType type)
      throws NoMeasurementException {
    Double ret = null;
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    List<?> result = entityManager
        .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp = :time",
            type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
    entityManager.getTransaction().commit();
    if (result.size() > 0) {
      for (IMeasurement p : (List<IMeasurement>) result) {
        if (p.getMeter().equals(meter)) {
          ret = p.getValue();
        }
      }
    }
    else {
      // need to get the stradle.
      entityManager.getTransaction().begin();
      List<?> before = entityManager
          .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp <= :time",
              type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
      List<?> after = entityManager
          .createQuery("FROM " + type.getMeasurementName() + " WHERE timestamp >= :time",
              type.getMeasurementClass()).setParameter("time", timestamp).getResultList();
      entityManager.getTransaction().commit();
      IMeasurement justBefore = null;
      for (IMeasurement p : (List<IMeasurement>) before) {
        if (p.getMeter().equals(meter)) {
          if (justBefore == null) {
            justBefore = p;
          }
          else if (p.getTimestamp().compareTo(justBefore.getTimestamp()) > 0) {
            justBefore = p;
          }
        }
      }
      IMeasurement justAfter = null;
      for (IMeasurement p : (List<IMeasurement>) after) {
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
    Double endVal = getValue(meter, end, WATER);
    Double startVal = getValue(meter, start, WATER);
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
    Double endVal = getValue(meter, end, gapSeconds, WATER);
    Double startVal = getValue(meter, start, gapSeconds, WATER);
    if (endVal != null && startVal != null) {
      return endVal - startVal;
    }
    return null;
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
  public List<WaterMeasurement> getWaterMeasurements(Meter meter, Date start, Date end) {
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
   * @return The water unit as a String.
   */
  public String getWaterUnit() {
    return waterUnit;
  }

  /**
   * Stores the given Location.
   * 
   * @param loc
   *          The Location.
   */
  public void putLocation(Location loc) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(loc);
    entityManager.getTransaction().commit();
  }

  /**
   * Stores the given EnergyMeasurment. This method will store EnergyMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The EnergyMeasurement to store.
   */
  public void putMeasurement(EnergyMeasurement meas) {
    if (getEnergyMeasurements(meas.getMeter(), meas.getTimestamp(), meas.getTimestamp()).size() == 0) {
      EntityManager entityManager = Server.getInstance().getEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(meas);
      for (Property p : meas.getMeter().getProperties()) {
        entityManager.persist(p);
      }
      entityManager.persist(meas.getMeter());
      entityManager.persist(meas.getMeter().getLocation());
      entityManager.persist(meas.getMeter().getModel());
      for (Property p : meas.getProperties()) {
        entityManager.persist(p);
      }
      entityManager.flush();
      entityManager.getTransaction().commit();
    }
    // else {
    // // already have an energy measurement for that meter at that time.
    // // what should we do?
    // }
  }

  /**
   * Stores the given PowerMeasurment. This method will store PowerMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void putMeasurement(PowerMeasurement meas) {
    if (getPowerMeasurements(meas.getMeter(), meas.getTimestamp(), meas.getTimestamp()).size() == 0) {
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
   * Stores the given TemperatureMeasurment. This method will store
   * TemperatureMeasurments that don't come from the same Meter.
   * 
   * @param meas
   *          The TemperatureMeasurement to store.
   */
  public void putMeasurement(TemperatureMeasurement meas) {
    if (getTemperatureMeasurements(meas.getMeter(), meas.getTimestamp(), meas.getTimestamp())
        .size() == 0) {
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
   * Stores the given WaterMeasurment. This method will store WaterMeasurments
   * that don't come from the same Meter.
   * 
   * @param meas
   *          The PowerMeasurement to store.
   */
  public void putMeasurement(WaterMeasurement meas) {
    if (getWaterMeasurements(meas.getMeter(), meas.getTimestamp(), meas.getTimestamp()).size() == 0) {
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
   * Stores the given Meter.
   * 
   * @param meter
   *          The Meter.
   */
  public void putMeter(Meter meter) {
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
   * Stores the given MeterModel.
   * 
   * @param model
   *          The MeterModel.
   */
  public void putMeterModel(MeterModel model) {
    EntityManager entityManager = Server.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(model);
    entityManager.getTransaction().commit();
  }
}
