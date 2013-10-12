/**
 * WattDepot.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot.server;

import java.util.List;

import org.wattdepot.server.datamodel.Location;
import org.wattdepot.server.datamodel.Sensor;
import org.wattdepot.server.datamodel.SensorGroup;
import org.wattdepot.server.datamodel.SensorModel;
import org.wattdepot.server.datamodel.SensorProcess;
import org.wattdepot.server.depository.WattDepository;

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
   * @return the defined Location.
   * @throws UniqueIdException
   *           if the id is already used for another Location.
   */
  public abstract Location defineLocation(String id, Double latitude, Double longitude,
      Double altitude, String description) throws UniqueIdException;

  /**
   * @param id
   *          The unique id.
   * @param uri
   *          The URI for the sensor.
   * @param l
   *          The location of the sensor
   * @param sm
   *          The SensorModel.
   * @return the defined Sensor.
   * @throws UniqueIdException
   *           if the id is already used for another Sensor.
   */
  public abstract Sensor defineSensor(String id, String uri, Location l, SensorModel sm)
      throws UniqueIdException;

  /**
   * @param id
   *          The unique id.
   * @param sensors
   *          one or more Sensors that make up the SensorGroup
   * @return the defined SensorGroup.
   * @throws UniqueIdException
   *           if the id is already used for another SensorGroup.
   */
  public abstract SensorGroup defineSensorGroup(String id, Sensor... sensors)
      throws UniqueIdException;

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
   * @return the defined SensorModel.
   * @throws UniqueIdException
   *           if the id is already used for another SensorModel.
   */
  public abstract SensorModel defineSensorModel(String id, String protocol, String type,
      String version) throws UniqueIdException;

  /**
   * Defines a new SensorProcess. This does not start any processes.
   * 
   * @param id
   *          The unique id.
   * @param sensor
   *          The Sensor to poll.
   * @param pollingInterval
   *          The polling interval.
   * @return The defined SensorProcess.
   * @throws UniqueIdException
   *           if the id is already used for another SensorProcess.
   */
  public abstract SensorProcess defineSensorProcess(String id, Sensor sensor, Long pollingInterval)
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
   * @return the defined WattDepository.
   * @throws UniqueIdException
   *           if the id is already used for another WattDepository.
   */
  public abstract WattDepository defineWattDepository(String id, String name, String measurementType)
      throws UniqueIdException;

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
   *          The unique id for the WattDepository to get.
   * @return The WattDepository with the given id.
   */
  public abstract WattDepository getWattDeposiory(String id);

  /**
   * @return The known/defined WattDepositories.
   */
  public abstract List<WattDepository> getWattDepositories();
}
