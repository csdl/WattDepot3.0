/**
 * WattDepotImpl.java created on Oct 30, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.Property;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.datamodel.UserPassword;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;
import org.wattdepot3.server.WattDepot;

/**
 * WattDepotImpl - Hibernate implementation of the WattDepot abstract class.
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotImpl extends WattDepot {

  /**
   * Default constructor.
   */
  public WattDepotImpl() {
    UserGroup admin = getUserGroup(UserGroup.ADMIN_GROUP.getId());
    if (admin == null) {
      try {
        defineUserGroup(UserGroup.ADMIN_GROUP.getId(), UserGroup.ADMIN_GROUP.getUsers());
      }
      catch (UniqueIdException e) {
        // what do we do here?
      }
    }
    try {
      defineUserPassword(UserPassword.ADMIN.getId(), UserPassword.ADMIN.getPlainText());
    }
    catch (UniqueIdException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    UserInfo adminUser = getUser(UserInfo.ADMIN.getId());
    if (adminUser == null) {
      try {
        defineUserInfo(UserInfo.ADMIN.getId(), UserInfo.ADMIN.getFirstName(),
            UserInfo.ADMIN.getLastName(), UserInfo.ADMIN.getEmail(), UserInfo.ADMIN.getAdmin(),
            UserInfo.ADMIN.getProperties());
      }
      catch (UniqueIdException e) {
        // what do we do here?
      }
    }
    else {
      updateUserInfo(UserInfo.ADMIN);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineLocation(java.lang.String,
   * java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String,
   * org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public Location defineLocation(String id, Double latitude, Double longitude, Double altitude,
      String description, UserGroup owner) throws UniqueIdException {
    Location l = null;
    try {
      l = getLocation(id, UserGroup.ADMIN_GROUP_NAME);
    }
    catch (MissMatchedOwnerException e) {
      // can't happen
      e.printStackTrace();
    }
    if (l != null) {
      throw new UniqueIdException(id + " is already a Location id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    l = new Location(id, latitude, longitude, altitude, description, owner);
    session.save(l);
    session.getTransaction().commit();
    session.close();
    return l;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineSensor(java.lang.String,
   * java.lang.String, org.wattdepot3.datamodel.Location,
   * org.wattdepot3.datamodel.SensorModel, org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public Sensor defineSensor(String id, String uri, Location l, SensorModel sm, UserGroup owner)
      throws UniqueIdException, MissMatchedOwnerException {
    if (!owner.equals(l.getOwner()) || !owner.equals(sm.getOwner())) {
      throw new MissMatchedOwnerException(owner.getId()
          + " does not match location's or model's owner.");
    }
    Sensor s = null;
    s = getSensor(id, UserGroup.ADMIN_GROUP_NAME);
    if (s != null) {
      throw new UniqueIdException(id + " is already a Sensor id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    s = new Sensor(id, uri, l, sm, owner);
    session.save(s);
    session.getTransaction().commit();
    session.close();
    return s;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineSensorGroup(java.lang.String,
   * java.util.List, org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public SensorGroup defineSensorGroup(String id, Set<Sensor> sensors, UserGroup owner)
      throws UniqueIdException, MissMatchedOwnerException {
    for (Sensor s : sensors) {
      if (!owner.equals(s.getOwner())) {
        throw new MissMatchedOwnerException(owner.getId() + " is not the owner of all the sensors.");
      }
    }
    SensorGroup sg = null;
    sg = getSensorGroup(id, UserGroup.ADMIN_GROUP_NAME);
    if (sg != null) {
      throw new UniqueIdException(id + " is already a SensorGroup id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    sg = new SensorGroup(id, sensors, owner);
    session.save(sg);
    session.getTransaction().commit();
    session.close();
    return sg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineSensorModel(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String,
   * org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public SensorModel defineSensorModel(String id, String protocol, String type, String version,
      UserGroup owner) throws UniqueIdException {
    SensorModel sm = null;
    try {
      sm = getSensorModel(id, UserGroup.ADMIN_GROUP_NAME);
    }
    catch (MissMatchedOwnerException e) {
      // can't happen
      e.printStackTrace();
    }
    if (sm != null) {
      throw new UniqueIdException(id + " is already a SensorModel id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    sm = new SensorModel(id, protocol, type, version, owner);
    session.save(sm);
    session.getTransaction().commit();
    session.close();
    return sm;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineSensorProcess(java.lang.String,
   * org.wattdepot3.datamodel.Sensor, java.lang.Long, java.lang.String,
   * org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public SensorProcess defineSensorProcess(String id, Sensor sensor, Long pollingInterval,
      String depositoryId, UserGroup owner) throws UniqueIdException, MissMatchedOwnerException {
    if (!owner.equals(sensor.getOwner())) {
      throw new MissMatchedOwnerException(owner.getId() + " does not own the sensor "
          + sensor.getId());
    }
    SensorProcess sp = null;
    sp = getSensorProcess(id, UserGroup.ADMIN_GROUP_NAME);
    if (sp != null) {
      throw new UniqueIdException(id + " is already a SensorModel id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    sp = new SensorProcess(id, sensor, pollingInterval, depositoryId, owner);
    session.save(sp);
    session.getTransaction().commit();
    session.close();
    return sp;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineUserGroup(java.lang.String,
   * java.util.List)
   */
  @Override
  public UserGroup defineUserGroup(String id, Set<UserInfo> users) throws UniqueIdException {
    UserGroup g = getUserGroup(id);
    if (g != null) {
      throw new UniqueIdException(id + " is already a UserGroup id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    for (UserInfo u : users) {
      session.saveOrUpdate(u);
    }
    g = new UserGroup(id, users);
    session.saveOrUpdate(g);
    session.getTransaction().commit();
    session.close();
    return g;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineUserInfo(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
   * java.lang.Boolean, java.util.Set)
   */
  @Override
  public UserInfo defineUserInfo(String id, String firstName, String lastName, String email,
      Boolean admin, Set<Property> properties) throws UniqueIdException {
    UserInfo u = getUser(id);
    if (u != null) {
      throw new UniqueIdException(id + " is already a UserInfo id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    u = new UserInfo(id, firstName, lastName, email, admin, properties);
    session.saveOrUpdate(u);
    session.getTransaction().commit();
    session.close();
    return u;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineUserPassword(java.lang.String,
   * java.lang.String)
   */
  @Override
  public UserPassword defineUserPassword(String id, String password) throws UniqueIdException {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    UserPassword up = new UserPassword(id, password);
    session.saveOrUpdate(up);
    session.getTransaction().commit();
    session.close();
    return up;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#defineWattDepository(java.lang.String,
   * java.lang.String, java.lang.String, org.wattdepot3.datamodel.UserGroup)
   */
  @Override
  public Depository defineWattDepository(String name, String measurementType, UserGroup owner)
      throws UniqueIdException {
    Depository d = null;
    try {
      d = getWattDeposiory(name, owner.getId());
    }
    catch (MissMatchedOwnerException e) {
      throw new UniqueIdException(name + " is used by another owner.");
    }
    if (d != null) {
      throw new UniqueIdException(name + " is already a Depository name.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    d = new Depository(name, measurementType, owner);
    session.save(d);
    session.getTransaction().commit();
    session.close();
    return d;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteLocation(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteLocation(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    Location l = getLocation(id, groupId);
    if (l != null) {
      Session session = Manager.getFactory().openSession();
      session.beginTransaction();
      session.delete(l);
      session.getTransaction().commit();
      session.close();
    }
    else {
      throw new IdNotFoundException(id + " was not found for owner " + groupId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteSensor(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteSensor(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    Sensor s = getSensor(id, groupId);
    if (s != null) {
      Session session = Manager.getFactory().openSession();
      session.beginTransaction();
      session.delete(s);
      session.getTransaction().commit();
      session.close();
    }
    else {
      throw new IdNotFoundException(id + " was not found for owner " + groupId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteSensorGroup(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteSensorGroup(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    SensorGroup s = getSensorGroup(id, groupId);
    if (s != null) {
      Session session = Manager.getFactory().openSession();
      session.beginTransaction();
      session.delete(s);
      session.getTransaction().commit();
      session.close();
    }
    else {
      throw new IdNotFoundException(id + " was not found for owner " + groupId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteSensorModel(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteSensorModel(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    SensorModel s = getSensorModel(id, groupId);
    if (s != null) {
      Session session = Manager.getFactory().openSession();
      session.beginTransaction();
      session.delete(s);
      session.getTransaction().commit();
      session.close();
    }
    else {
      throw new IdNotFoundException(id + " was not found for owner " + groupId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteSensorProcess(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteSensorProcess(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    SensorProcess s = getSensorProcess(id, groupId);
    if (s != null) {
      Session session = Manager.getFactory().openSession();
      session.beginTransaction();
      session.delete(s);
      session.getTransaction().commit();
      session.close();
    }
    else {
      throw new IdNotFoundException(id + " was not found for owner " + groupId);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteUser(java.lang.String)
   */
  @Override
  public void deleteUser(String id) throws IdNotFoundException {
    UserInfo u = getUser(id);
    if (u == null) {
      throw new IdNotFoundException(id + " is not a defined user id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.delete(u);
    session.getTransaction().commit();
    session.close();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteUserGroup(java.lang.String)
   */
  @Override
  public void deleteUserGroup(String id) throws IdNotFoundException {
    UserGroup g = getUserGroup(id);
    if (g == null) {
      throw new IdNotFoundException(id + " is not a defined user group id.");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.delete(g);
    session.getTransaction().commit();
    session.close();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#deleteWattDepository(java.lang.String,
   * java.lang.String)
   */
  @Override
  public void deleteWattDepository(String id, String groupId) throws IdNotFoundException,
      MissMatchedOwnerException {
    Depository d = getWattDeposiory(id, groupId);
    if (d == null) {
      throw new IdNotFoundException(id + " is not a defined depository");
    }
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.delete(d);
    session.getTransaction().commit();
    session.close();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getLocation(java.lang.String,
   * java.lang.String)
   */
  @Override
  public Location getLocation(String id, String groupId) throws MissMatchedOwnerException {
    // search through all the known locations
    for (Location l : getLocations(UserGroup.ADMIN_GROUP_NAME)) {
      if (l.getId().equals(id)) {
        if (l.getOwner().getId().equals(groupId) || groupId.equals(UserGroup.ADMIN_GROUP_NAME)) {
          return l;
        }
        else {
          throw new MissMatchedOwnerException(id + " is not owned by " + groupId);
        }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getLocationIds()
   */
  @Override
  public List<String> getLocationIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (Location l : getLocations(groupId)) {
      ret.add(l.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getLocations(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Location> getLocations(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Location").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<Location> ret = new ArrayList<Location>();
    for (Location d : (List<Location>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        ret.add(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensor(java.lang.String,
   * java.lang.String)
   */
  @Override
  public Sensor getSensor(String id, String groupId) throws MissMatchedOwnerException {
    for (Sensor s : getSensors(UserGroup.ADMIN_GROUP_NAME)) {
      if (s.getId().equals(id)) {
        if (s.getOwner().getId().equals(groupId) || groupId.contains(UserGroup.ADMIN_GROUP_NAME)) {
          Hibernate.initialize(s.getLocation());
          Hibernate.initialize(s.getModel());
          return s;
        }
        else {
          throw new MissMatchedOwnerException(id + " is not owned by " + groupId);
        }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorGroup(java.lang.String,
   * java.lang.String)
   */
  @Override
  public SensorGroup getSensorGroup(String id, String groupId) throws MissMatchedOwnerException {
    for (SensorGroup s : getSensorGroups(UserGroup.ADMIN_GROUP_NAME)) {
      if (s.getId().equals(id)) {
        if (s.getOwner().getId().equals(groupId) || groupId.contains(UserGroup.ADMIN_GROUP_NAME)) {
          for (Sensor sens : s.getSensors()) {
            Hibernate.initialize(sens);
            Hibernate.initialize(sens.getLocation());
            Hibernate.initialize(sens.getModel());
          }
          return s;
        }
        else {
          throw new MissMatchedOwnerException(id + " is not owned by " + groupId);
        }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorGroupIds()
   */
  @Override
  public List<String> getSensorGroupIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (SensorGroup s : getSensorGroups(groupId)) {
      ret.add(s.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorGroups(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<SensorGroup> getSensorGroups(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from SensorGroup").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<SensorGroup> ret = new ArrayList<SensorGroup>();
    for (SensorGroup d : (List<SensorGroup>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        for (Sensor sens : d.getSensors()) {
          Hibernate.initialize(sens);
          Hibernate.initialize(sens.getLocation());
          Hibernate.initialize(sens.getModel());
        }
        ret.add(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorIds()
   */
  @Override
  public List<String> getSensorIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (Sensor s : getSensors(groupId)) {
      ret.add(s.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorModel(java.lang.String,
   * java.lang.String)
   */
  @Override
  public SensorModel getSensorModel(String id, String groupId) throws MissMatchedOwnerException {
    for (SensorModel s : getSensorModels(UserGroup.ADMIN_GROUP_NAME)) {
      if (s.getId().equals(id)) {
        if (s.getOwner().getId().equals(groupId) || groupId.contains(UserGroup.ADMIN_GROUP_NAME)) {
          return s;
        }
        else {
          throw new MissMatchedOwnerException(id + " is not owned by " + groupId);
        }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorModelIds()
   */
  @Override
  public List<String> getSensorModelIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (SensorModel s : getSensorModels(groupId)) {
      ret.add(s.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorModels(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<SensorModel> getSensorModels(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from SensorModel").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<SensorModel> ret = new ArrayList<SensorModel>();
    for (SensorModel d : (List<SensorModel>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        ret.add(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorProcess(java.lang.String,
   * java.lang.String)
   */
  @Override
  public SensorProcess getSensorProcess(String id, String groupId) throws MissMatchedOwnerException {
    for (SensorProcess s : getSensorProcesses(UserGroup.ADMIN_GROUP_NAME)) {
      if (s.getId().equals(id)) {
        if (s.getOwner().getId().equals(groupId) || groupId.contains(UserGroup.ADMIN_GROUP_NAME)) {
          return s;
        }
        else {
          throw new MissMatchedOwnerException(id + " is not owned by " + groupId);
        }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorProcesses(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<SensorProcess> getSensorProcesses(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from SensorProcess").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<SensorProcess> ret = new ArrayList<SensorProcess>();
    for (SensorProcess d : (List<SensorProcess>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        ret.add(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensorProcessIds()
   */
  @Override
  public List<String> getSensorProcessIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (SensorProcess s : getSensorProcesses(groupId)) {
      ret.add(s.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getSensors(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Sensor> getSensors(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Sensor").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<Sensor> ret = new ArrayList<Sensor>();
    for (Sensor d : (List<Sensor>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        ret.add(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUser(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public UserInfo getUser(String id) {
    UserInfo ret = null;
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from UserInfo").list();
    session.getTransaction().commit();
    session.close();
    for (UserInfo u : (List<UserInfo>) result) {
      if (id.equals(u.getId())) {
        ret = u;
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUserGroup(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public UserGroup getUserGroup(String id) {
    UserGroup ret = null;
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from UserGroup").list();
    session.getTransaction().commit();
    session.close();
    for (UserGroup g : (List<UserGroup>) result) {
      if (id.equals(g.getId())) {
        ret = g;
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUserGroupIds()
   */
  @Override
  public List<String> getUserGroupIds() {
    ArrayList<String> ret = new ArrayList<String>();
    for (UserGroup u : getUserGroups()) {
      ret.add(u.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUserGroups()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<UserGroup> getUserGroups() {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from UserGroup").list();
    session.getTransaction().commit();
    session.close();
    return (List<UserGroup>) result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUserIds()
   */
  @Override
  public List<String> getUserIds() {
    ArrayList<String> ret = new ArrayList<String>();
    for (UserInfo u : getUsers()) {
      ret.add(u.getId());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUserPassword(java.lang.String)
   */
  @Override
  public UserPassword getUserPassword(String id) {
    UserPassword ret = null;
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("unchecked")
    List<UserPassword> result = (List<UserPassword>) session.createQuery("from UserPassword")
        .list();
    for (UserPassword up : result) {
      if (up.getId().equals(id)) {
        ret = up;
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getUsers()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<UserInfo> getUsers() {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from UserInfo").list();
    session.getTransaction().commit();
    session.close();
    return (List<UserInfo>) result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#getUsersGroup(org.wattdepot3.datamodel.
   * UserInfo)
   */
  @Override
  public UserGroup getUsersGroup(UserInfo user) {
    for (UserGroup group : getUserGroups()) {
      if (group.getUsers().contains(user)) {
        return group;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getWattDeposiory(java.lang.String,
   * java.lang.String)
   */
  @Override
  public Depository getWattDeposiory(String id, String groupId) throws MissMatchedOwnerException {
    List<Depository> all = getWattDepositories(groupId);
    Depository ret = null;
    for (Depository d : all) {
      if (d.getName().equals(id)) {
        ret = new DepositoryImpl(d);
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getWattDepositories(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Depository> getWattDepositories(String groupId) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    @SuppressWarnings("rawtypes")
    List result = session.createQuery("from Depository").list();
    session.getTransaction().commit();
    session.close();
    ArrayList<Depository> ret = new ArrayList<Depository>();
    for (Depository d : (List<Depository>) result) {
      if (groupId.equals(UserGroup.ADMIN_GROUP_NAME) || groupId.equals(d.getOwner().getId())) {
        ret.add(new DepositoryImpl(d));
      }
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.server.WattDepot#getWattDepositoryIds()
   */
  @Override
  public List<String> getWattDepositoryIds(String groupId) {
    ArrayList<String> ret = new ArrayList<String>();
    for (Depository d : getWattDepositories(groupId)) {
      ret.add(d.getName());
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateLocation(org.wattdepot3.datamodel
   * .Location)
   */
  @Override
  public Location updateLocation(Location loc) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(loc);
    session.getTransaction().commit();
    session.close();

    return loc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateSensor(org.wattdepot3.datamodel.Sensor
   * )
   */
  @Override
  public Sensor updateSensor(Sensor sensor) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(sensor);
    session.getTransaction().commit();
    session.close();

    return sensor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateSensorGroup(org.wattdepot3.datamodel
   * .SensorGroup)
   */
  @Override
  public SensorGroup updateSensorGroup(SensorGroup group) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(group);
    session.getTransaction().commit();
    session.close();

    return group;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateSensorModel(org.wattdepot3.datamodel
   * .SensorModel)
   */
  @Override
  public SensorModel updateSensorModel(SensorModel model) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(model);
    session.getTransaction().commit();
    session.close();

    return model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateSensorProcess(org.wattdepot3.datamodel
   * .SensorProcess)
   */
  @Override
  public SensorProcess updateSensorProcess(SensorProcess process) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(process);
    session.getTransaction().commit();
    session.close();

    return process;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateUserGroup(org.wattdepot3.datamodel
   * .UserGroup)
   */
  @Override
  public UserGroup updateUserGroup(UserGroup group) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(group);
    session.getTransaction().commit();
    session.close();

    return group;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.server.WattDepot#updateUserInfo(org.wattdepot3.datamodel
   * .UserInfo)
   */
  @Override
  public UserInfo updateUserInfo(UserInfo user) {
    Session session = Manager.getFactory().openSession();
    session.beginTransaction();
    session.saveOrUpdate(user);
    session.getTransaction().commit();
    session.close();
    return user;
  }

}
