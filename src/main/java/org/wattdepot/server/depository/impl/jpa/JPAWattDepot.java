/**
 * JpaWattDepot.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.wattdepot.server.UniqueIdException;
import org.wattdepot.server.WattDepot;
import org.wattdepot.server.datamodel.Location;
import org.wattdepot.server.datamodel.Sensor;
import org.wattdepot.server.datamodel.SensorGroup;
import org.wattdepot.server.datamodel.SensorModel;
import org.wattdepot.server.depository.WattDepository;

/**
 * JpaWattDepot is the JPA implementation of WattDepot.
 * 
 * @author Cam Moore
 * 
 */
public class JPAWattDepot extends WattDepot {
  private Map<String, JPAWattDepository> depositories;
  private Map<String, JPASensorGroup> sensorGroups;
  private Map<String, JPASensor> sensors;
  private Map<String, JPASensorModel> sensorModels;
  private Map<String, JPALocation> locations;

  /** Default constructor. */
  public JPAWattDepot() {
    this.depositories = new HashMap<String, JPAWattDepository>();
    this.locations = new HashMap<String, JPALocation>();
    this.sensorGroups = new HashMap<String, JPASensorGroup>();
    this.sensorModels = new HashMap<String, JPASensorModel>();
    this.sensors = new HashMap<String, JPASensor>();

    // Initialize the hash tables from the persistent store.
    EntityManager entityManager = JPAManager.getInstance().getEntityManager();
    // depositories
    entityManager.getTransaction().begin();
    List<JPAWattDepositoryDescription> wdds = entityManager.createQuery(
        "from JPAWattDepositoryDescription", JPAWattDepositoryDescription.class).getResultList();
    for (JPAWattDepositoryDescription wdd : wdds) {
      JPAWattDepository depo = new JPAWattDepository(wdd.getName(), wdd.getMeasurementType());
      depositories.put(depo.getName(), depo);
    }
    entityManager.getTransaction().commit();
    // sensor groups
    entityManager.getTransaction().begin();
    List<JPASensorGroup> sgs = entityManager.createQuery("from JPASensorGroup", JPASensorGroup.class)
        .getResultList();
    for (JPASensorGroup sg : sgs) {
      sensorGroups.put(sg.getUniqueId(), sg);
    }
    entityManager.getTransaction().commit();
    // sensors
    entityManager.getTransaction().begin();
    List<JPASensor> ss = entityManager.createQuery("from JPASensor", JPASensor.class).getResultList();
    for (JPASensor s : ss) {
      sensors.put(s.getUniqueId(), s);
    }
    entityManager.getTransaction().commit();
    // sensor models
    entityManager.getTransaction().begin();
    List<JPASensorModel> sms = entityManager.createQuery("from JPASensorModel", JPASensorModel.class)
        .getResultList();
    for (JPASensorModel sm : sms) {
      sensorModels.put(sm.getUniqueId(), sm);
    }
    entityManager.getTransaction().commit();
    // locations
    entityManager.getTransaction().begin();
    List<JPALocation> locs = entityManager.createQuery("from JPALocation", JPALocation.class)
        .getResultList();
    for (JPALocation l : locs) {
      locations.put(l.getUniqueId(), l);
    }
    entityManager.getTransaction().commit();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getWattDepositories()
   */
  @Override
  public List<WattDepository> getWattDepositories() {
    ArrayList<WattDepository> ret = new ArrayList<WattDepository>();
    for (JPAWattDepository d : depositories.values()) {
      ret.add(d);
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getWattDeposiory(java.lang.String)
   */
  @Override
  public WattDepository getWattDeposiory(String id) {
    return depositories.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensorGroups()
   */
  @Override
  public List<SensorGroup> getSensorGroups() {
    ArrayList<SensorGroup> ret = new ArrayList<SensorGroup>();
    for (JPASensorGroup s : sensorGroups.values()) {
      ret.add(s);
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensorGroup(java.lang.String)
   */
  @Override
  public SensorGroup getSensorGroup(String id) {
    return sensorGroups.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensors()
   */
  @Override
  public List<Sensor> getSensors() {
    ArrayList<Sensor> ret = new ArrayList<Sensor>();
    for (JPASensor s : sensors.values()) {
      ret.add(s);
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensor(java.lang.String)
   */
  @Override
  public Sensor getSensor(String id) {
    return sensors.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensorModels()
   */
  @Override
  public List<SensorModel> getSensorModels() {
    ArrayList<SensorModel> ret = new ArrayList<SensorModel>();
    for (JPASensorModel s : sensorModels.values()) {
      ret.add(s);
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getSensorModel(java.lang.String)
   */
  @Override
  public SensorModel getSensorModel(String id) {
    return sensorModels.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getLocations()
   */
  @Override
  public List<Location> getLocations() {
    ArrayList<Location> ret = new ArrayList<Location>();
    for (JPALocation l : locations.values()) {
      ret.add(l);
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#getLocation(java.lang.String)
   */
  @Override
  public Location getLocation(String id) {
    return locations.get(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#defineLocation(java.lang.String,
   * java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String)
   */
  @Override
  public Location defineLocation(String id, Double latitude, Double longitude, Double altitude,
      String description) throws UniqueIdException {
    if (locations.containsKey(id)) {
      throw new UniqueIdException(id + " is already a Location unique id.");
    }
    JPALocation loc = new JPALocation(id, latitude, longitude, altitude, description);
    locations.put(loc.getUniqueId(), loc);
    EntityManager entityManager = JPAManager.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(loc);
    entityManager.getTransaction().commit();
    return loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#defineSensorModel(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public SensorModel defineSensorModel(String id, String protocol, String type, String version)
      throws UniqueIdException {
    if (sensorModels.containsKey(id)) {
      throw new UniqueIdException(id + " is already a SensorModel unique id.");
    }
    JPASensorModel sm = new JPASensorModel(id, protocol, type, version);
    sensorModels.put(sm.getUniqueId(), sm);
    EntityManager entityManager = JPAManager.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(sm);
    entityManager.getTransaction().commit();
    return sm;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#defineSensorGroup(java.lang.String,
   * org.wattdepot.server.datamodel.Sensor[])
   */
  @Override
  public SensorGroup defineSensorGroup(String id, Sensor... ss) throws UniqueIdException {
    if (sensorGroups.containsKey(id)) {
      throw new UniqueIdException(id + " is aready a SensorGroup unique id.");
    }
    JPASensorGroup sg = new JPASensorGroup(id);
    for (Sensor s : ss) {
      sg.add(new JPASensor(s));
    }
    sensorGroups.put(sg.getUniqueId(), sg);
    EntityManager entityManager = JPAManager.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(sg);
    entityManager.getTransaction().commit();
    return sg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot.server.WattDepot#defineSensor(java.lang.String,
   * java.lang.String, org.wattdepot.server.datamodel.Location,
   * org.wattdepot.server.datamodel.SensorModel)
   */
  @Override
  public Sensor defineSensor(String id, String uri, Location l, SensorModel sm)
      throws UniqueIdException {
    if (sensors.containsKey(id)) {
      throw new UniqueIdException(id + " is already a Sensor unique id.");
    }
    JPASensor s = new JPASensor(id, uri, new JPALocation(l), new JPASensorModel(sm));
    sensors.put(s.getUniqueId(), s);
    EntityManager entityManager = JPAManager.getInstance().getEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(s);
    entityManager.getTransaction().commit();
    return s;
  }

}
