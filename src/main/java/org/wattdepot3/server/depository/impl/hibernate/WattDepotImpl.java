/**
 * WattDepotImpl.java created on Oct 30, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		// need to ensure the ADMIN user is in the database.
		Session session = Manager.getFactory().openSession();
		session.beginTransaction();
		UserInfo a = (UserInfo) session.get(UserInfo.class,
				UserInfo.ADMIN.getId());
		if (a == null) {
			session.save(UserInfo.ADMIN);
		}
		session.getTransaction().commit();
		session.close();
		// make sure ADMIN_GROUP is in the database.
		session = Manager.getFactory().openSession();
		session.beginTransaction();
		UserGroup ua = (UserGroup) session.get(UserGroup.class,
				UserGroup.ADMIN_GROUP.getId());
		if (ua == null) {
			session.save(UserGroup.ADMIN_GROUP);
		}
		session.getTransaction().commit();
		session.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#defineLocation(java.lang.String,
	 * java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String,
	 * org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public Location defineLocation(String id, Double latitude,
			Double longitude, Double altitude, String description,
			UserGroup owner) throws UniqueIdException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#defineSensor(java.lang.String,
	 * java.lang.String, org.wattdepot3.datamodel.Location,
	 * org.wattdepot3.datamodel.SensorModel, org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public Sensor defineSensor(String id, String uri, Location l,
			SensorModel sm, UserGroup owner) throws UniqueIdException,
			MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#defineSensorGroup(java.lang.String,
	 * java.util.List, org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public SensorGroup defineSensorGroup(String id, List<Sensor> sensors,
			UserGroup owner) throws UniqueIdException,
			MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#defineSensorModel(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String,
	 * org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public SensorModel defineSensorModel(String id, String protocol,
			String type, String version, UserGroup owner)
			throws UniqueIdException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wattdepot3.server.WattDepot#defineSensorProcess(java.lang.String,
	 * org.wattdepot3.datamodel.Sensor, java.lang.Long, java.lang.String,
	 * org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public SensorProcess defineSensorProcess(String id, Sensor sensor,
			Long pollingInterval, String depositoryId, UserGroup owner)
			throws UniqueIdException, MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#defineUserGroup(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public UserGroup defineUserGroup(String id, Set<UserInfo> users)
			throws UniqueIdException {
		UserGroup g = getUserGroup(id);
		if (g != null) {
			throw new UniqueIdException(id + " is already a UserGroup id.");
		}
		Session session = Manager.getFactory().openSession();
		session.beginTransaction();
		g = new UserGroup(id, users);
		session.save(g);
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
	public UserInfo defineUserInfo(String id, String firstName,
			String lastName, String email, String password, Boolean admin,
			Set<Property> properties) throws UniqueIdException {
		UserInfo u = getUser(id);
		if (u != null) {
			throw new UniqueIdException(id + " is already a UserInfo id.");
		}
		Session session = Manager.getFactory().openSession();
		session.beginTransaction();
		u = new UserInfo(id, firstName, lastName, email, password, admin,
				properties);
		session.save(u);
		session.getTransaction().commit();
		session.close();
		return u;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wattdepot3.server.WattDepot#defineWattDepository(java.lang.String,
	 * java.lang.String, java.lang.String, org.wattdepot3.datamodel.UserGroup)
	 */
	@Override
	public Depository defineWattDepository(String id, String name,
			String measurementType, UserGroup owner) throws UniqueIdException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#deleteLocation(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteLocation(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#deleteSensor(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteSensor(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#deleteSensorGroup(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteSensorGroup(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#deleteSensorModel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteSensorModel(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wattdepot3.server.WattDepot#deleteSensorProcess(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteSensorProcess(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

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
			throw new IdNotFoundException(id
					+ " is not a defined user group id.");
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
	 * @see
	 * org.wattdepot3.server.WattDepot#deleteWattDepository(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void deleteWattDepository(String id, String groupId)
			throws IdNotFoundException, MissMatchedOwnerException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#getLocation(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Location getLocation(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
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
	public Sensor getSensor(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wattdepot3.server.WattDepot#getSensorGroup(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SensorGroup getSensorGroup(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
				ret.add(d);
			}
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
	public SensorModel getSensorModel(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
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
	public SensorProcess getSensorProcess(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
				ret.add(d);
			}
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
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
	 * @see org.wattdepot3.server.WattDepot#getWattDeposiory(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Depository getWattDeposiory(String id, String groupId)
			throws MissMatchedOwnerException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wattdepot3.server.WattDepot#getWattDepositories(java.lang.String)
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
			if (groupId.equals("admin") || groupId.equals(d.getOwner().getId())) {
				ret.add(d);
			}
		}
		return ret;
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

}
