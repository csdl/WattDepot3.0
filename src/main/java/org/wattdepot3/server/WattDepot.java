/**
 * WattDepot.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.server;

import java.util.List;
import java.util.Set;

import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.Property;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.UserGroup;
import org.wattdepot3.datamodel.UserInfo;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.UniqueIdException;

/**
 * WattDepot abstract interface.
 * 
 * @author Cam Moore
 * 
 */
public abstract class WattDepot {

  /**
   * Defines a new Location in WattDepot.
   * 
   * @param id
   *          The unique id.
   * @param latitude
   *          The decimal Latitude.
   * @param longitude
   *          The decimal Longitude.
   * @param altitude
   *          The altitude in meters w.r.t. MSL.
   * @param description
   *          A String description of the Location.
   * @param owner
   *          The owner of the Location.
   * @return the defined Location.
   * @throws UniqueIdException
   *           if the id is already used for another Location.
   */
  public abstract Location defineLocation(String id, Double latitude, Double longitude,
      Double altitude, String description, UserGroup owner) throws UniqueIdException;

  /**
   * @param id
   *          The unique id.
   * @param uri
   *          The URI for the sensor.
   * @param l
   *          The location of the sensor
   * @param sm
   *          The SensorModel.
   * @param owner
   *          the owner of the Sensor.
   * @return the defined Sensor.
   * @throws UniqueIdException
   *           if the id is already used for another Sensor.
   * @throws MissMatchedOwnerException
   *           if the given owner doesn't match the owners of the Location or
   *           SensorModel.
   */
  public abstract Sensor defineSensor(String id, String uri, Location l, SensorModel sm,
      UserGroup owner) throws UniqueIdException, MissMatchedOwnerException;

  /**
   * @param id
   *          The unique id.
   * @param sensors
   *          A list of the Sensors that make up the SensorGroup
   * @param owner
   *          the owner of the SensorGroup.
   * @return the defined SensorGroup.
   * @throws UniqueIdException
   *           if the id is already used for another SensorGroup.
   * @throws MissMatchedOwnerException
   *           if the given owner doesn't match the owners of the Sensors.
   */
  public abstract SensorGroup defineSensorGroup(String id, List<Sensor> sensors, UserGroup owner)
      throws UniqueIdException, MissMatchedOwnerException;

  /**
   * Defines a new SensorModel in WattDepot.
   * 
   * @param id
   *          The unique id.
   * @param protocol
   *          The protocol used by a meter.
   * @param type
   *          The type of the meter.
   * @param version
   *          The version the meter is using.
   * @param owner
   *          the owner of the SensorModel.
   * @return the defined SensorModel.
   * @throws UniqueIdException
   *           if the id is already used for another SensorModel.
   */
  public abstract SensorModel defineSensorModel(String id, String protocol, String type,
      String version, UserGroup owner) throws UniqueIdException;

  /**
   * Defines a new SensorProcess. This does not start any processes.
   * 
   * @param id
   *          The unique id.
   * @param sensor
   *          The Sensor to poll.
   * @param pollingInterval
   *          The polling interval.
   * @param depositoryId
   *          The id of the depository to use.
   * @param owner
   *          the owner of the SensorProcess.
   * @return The defined SensorProcess.
   * @throws UniqueIdException
   *           if the id is already used for another SensorProcess.
   * @throws MissMatchedOwnerException
   *           if the given owner doesn't match the owners of the Sensor or
   *           Depository.
   */
  public abstract SensorProcess defineSensorProcess(String id, Sensor sensor, Long pollingInterval,
      String depositoryId, UserGroup owner) throws UniqueIdException, MissMatchedOwnerException;

  /**
   * @param id
   *          The unique id.
   * @param users
   *          The members of the group.
   * @return The defined UserGroup.
   * @throws UniqueIdException
   *           If the id is already used for another UserGroup.
   */
  public abstract UserGroup defineUserGroup(String id, List<UserInfo> users)
      throws UniqueIdException;

  /**
   * Defines a new UserInfo with the given information.
   * 
   * @param id
   *          The unique id.
   * @param firstName
   *          The user's name.
   * @param lastName
   *          The user's last name.
   * @param email
   *          The user's email address.
   * @param password
   *          The user's password.
   * @param admin
   *          True if they are an admin.
   * @param properties
   *          The additional properties of the user.
   * @return The defined UserInfo.
   * @throws UniqueIdException
   *           if the id is already used for another UserInfo.
   */
  public abstract UserInfo defineUserInfo(String id, String firstName, String lastName,
      String email, String password, Boolean admin, Set<Property> properties)
      throws UniqueIdException;

  /**
   * Defines a new WattDepository in WattDepot.
   * 
   * @param id
   *          The unique id.
   * @param name
   *          The name.
   * @param measurementType
   *          the Measurement Type.
   * @param owner
   *          the owner of the WattDepository.
   * @return the defined WattDepository.
   * @throws UniqueIdException
   *           if the id is already used for another WattDepository.
   */
  public abstract Depository defineWattDepository(String id, String name, String measurementType,
      UserGroup owner) throws UniqueIdException;

  /**
   * Deletes the given location.
   * 
   * @param id
   *          The unique id of the location to delete.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteLocation(String id) throws IdNotFoundException;

  /**
   * Deletes the given Sensor.
   * 
   * @param id
   *          The unique id of the Sensor.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteSensor(String id) throws IdNotFoundException;

  /**
   * Deletes the given SensorGroup.
   * 
   * @param id
   *          The unique id of the SensorGroup.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteSensorGroup(String id) throws IdNotFoundException;

  /**
   * Deletes the given SensorModel.
   * 
   * @param id
   *          The unique id of the SensorModel.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteSensorModel(String id) throws IdNotFoundException;

  /**
   * Deletes the given SensorProcess.
   * 
   * @param id
   *          The unique id of the SensorProcess.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteSensorProcess(String id) throws IdNotFoundException;

  /**
   * @param id
   *          The unique id of the User.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteUser(String id) throws IdNotFoundException;

  /**
   * @param id
   *          The unique id of the UserGroup.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteUserGroup(String id) throws IdNotFoundException;

  /**
   * Deletes the given WattDepository.
   * 
   * @param id
   *          The unique id of the WattDepository.
   * @throws IdNotFoundException
   *           If the id is not known or defined.
   */
  public abstract void deleteWattDepository(String id) throws IdNotFoundException;

  /**
   * @param id
   *          The unique id for the Location.
   * @return The Location with the given id.
   */
  public abstract Location getLocation(String id);

  /**
   * @return The known/defined Locations.
   */
  public abstract List<Location> getLocations();

  /**
   * @param id
   *          The unique id for the Sensor.
   * @return The Sensor with the given id.
   */
  public abstract Sensor getSensor(String id);

  /**
   * @param id
   *          The unique id for the SensorGroup.
   * @return The SensorGroup with the given id.
   */
  public abstract SensorGroup getSensorGroup(String id);

  /**
   * @return The known/defined SensorGroups.
   */
  public abstract List<SensorGroup> getSensorGroups();

  /**
   * @param id
   *          The unique id for the SensorModel.
   * @return The SensorModel with the given id.
   */
  public abstract SensorModel getSensorModel(String id);

  /**
   * @return The known/defined SensorModels.
   */
  public abstract List<SensorModel> getSensorModels();

  /**
   * @param id
   *          The unique id for the SensorProcess.
   * @return The SensorProcess with the given id.
   */
  public abstract SensorProcess getSensorProcess(String id);

  /**
   * @return The known/defined SensorProcesses.
   */
  public abstract List<SensorProcess> getSensorProcesses();

  /**
   * @return The known/defined Sensors.
   */
  public abstract List<Sensor> getSensors();

  /**
   * @param id
   *          the unique id for the UserInfo.
   * @return The UserInfo with the given id.
   */
  public abstract UserInfo getUser(String id);

  /**
   * @param id
   *          the unique id for the UserGroup.
   * @return The UserGroup with the given id.
   */
  public abstract UserGroup getUserGroup(String id);

  /**
   * @return The known/defined UserGroups.
   */
  public abstract List<UserGroup> getUserGroups();

  /**
   * @return The known/defined UserInfos.
   */
  public abstract List<UserInfo> getUsers();

  /**
   * @param id
   *          The unique id for the WattDepository to get.
   * @return The WattDepository with the given id.
   */
  public abstract Depository getWattDeposiory(String id);

  /**
   * @return The known/defined WattDepositories.
   */
  public abstract List<Depository> getWattDepositories();
}
