/**
 * WattDepotCRUD.java putd on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import java.util.Date;

import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.DepositoryList;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.LocationList;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.MeasurementList;
import org.wattdepot3.datamodel.MeasurementType;
import org.wattdepot3.datamodel.MeasurementTypeList;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorGroupList;
import org.wattdepot3.datamodel.SensorList;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorModelList;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.datamodel.SensorProcessList;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MeasurementGapException;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.NoMeasurementException;

/**
 * WattDepotInterface - The CRUD interface to the WattDepot3 server for regular
 * users.
 * 
 * @author Cam Moore
 * 
 */
public interface WattDepotInterface {

  /**
   * Deletes the given Depository.
   * 
   * @param depository
   *          the Depository to delete.
   * @throws IdNotFoundException
   *           if the Depository is not found in the server.
   */
  public void deleteDepository(Depository depository) throws IdNotFoundException;

  /**
   * Deletes the given Location.
   * 
   * @param location
   *          the Location to delete.
   * @throws IdNotFoundException
   *           if the Location is not found in the server.
   */
  public void deleteLocation(Location location) throws IdNotFoundException;

  /**
   * Deletes the given measurement from the given depository.
   * 
   * @param depository
   *          the Depository that stores the measurement.
   * @param measurement
   *          the Measurement to delete.
   * @throws IdNotFoundException
   *           if the Measurement is not found in the Depository.
   */
  public void deleteMeasurement(Depository depository, Measurement measurement)
      throws IdNotFoundException;

  /**
   * Deletes the given MeasurementType.
   * 
   * @param type
   *          the measurement type to delete.
   */
  public void deleteMeasurementType(MeasurementType type);

  /**
   * Deletes the given Sensor.
   * 
   * @param sensor
   *          the Sensor to delete.
   * @throws IdNotFoundException
   *           if the Sensor is not found in the server.
   */
  public void deleteSensor(Sensor sensor) throws IdNotFoundException;

  /**
   * Deletes the given SensorGroup.
   * 
   * @param group
   *          the SensorGroup to delete.
   * @throws IdNotFoundException
   *           if the SensorGroup is not found in the server.
   */
  public void deleteSensorGroup(SensorGroup group) throws IdNotFoundException;

  /**
   * Deletes the given SensorModel.
   * 
   * @param model
   *          the SensorModel to delete.
   * @throws IdNotFoundException
   *           if the SensorModel is not found in the server.
   */
  public void deleteSensorModel(SensorModel model) throws IdNotFoundException;

  /**
   * Deletes the given SensorProcess.
   * 
   * @param process
   *          the SensorProcess to delete.
   * @throws IdNotFoundException
   *           if the SensorProcess is not found in the server.
   */
  public void deleteSensorProcess(SensorProcess process) throws IdNotFoundException;

  /**
   * @return The defined Depositories.
   */
  public DepositoryList getDepositories();

  /**
   * Retrieves the Depository with the given id from the WattDepot Server.
   * 
   * @param id
   *          The Depository's id.
   * @return the Depository with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a Depository's id.
   */
  public Depository getDepository(String id) throws IdNotFoundException;

  /**
   * Retrieves the Location with the given id from the WattDepot Server.
   * 
   * @param id
   *          The Location's id.
   * @return the Location with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a Location's id.
   */
  public Location getLocation(String id) throws IdNotFoundException;

  /**
   * @return The defined Locations.
   */
  public LocationList getLocations();

  /**
   * @param depository
   *          The Depository storing the Measurements.
   * @param sensor
   *          The Sensor that made the measurements.
   * @param start
   *          The start time.
   * @param end
   *          The end time.
   * @return The Measurements stored in the depository made by the sensor
   *         between start and end.
   */
  public MeasurementList getMeasurements(Depository depository, Sensor sensor, Date start, Date end);

  /**
   * @return The defined MeasurementTypes.
   */
  public MeasurementTypeList getMeasurementTypes();

  /**
   * @param id
   *          the unique id for the MeasurementType.
   * @return The MeasurementType.
   * @exception IdNotFoundException
   *              if the given id is not a MeasurementType's id.
   */
  public MeasurementType getMeasurementType(String id) throws IdNotFoundException;

  /**
   * Retrieves the Sensor with the given id from the WattDepot Server.
   * 
   * @param id
   *          The Sensor's id.
   * @return the Sensor with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a Sensor's id.
   */
  public Sensor getSensor(String id) throws IdNotFoundException;

  /**
   * Retrieves the SensorGroup with the given id from the WattDepot Server.
   * 
   * @param id
   *          The SensorGroup's id.
   * @return the SensorGroup with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a SensorGroup's id.
   */
  public SensorGroup getSensorGroup(String id) throws IdNotFoundException;

  /**
   * @return The defined SensorGroups.
   */
  public SensorGroupList getSensorGroups();

  /**
   * Retrieves the SensorModel with the given id from the WattDepot Server.
   * 
   * @param id
   *          The SensorModel's id.
   * @return the SensorModel with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a SensorModel's id.
   */
  public SensorModel getSensorModel(String id) throws IdNotFoundException;

  /**
   * @return The defined SensorModels.
   */
  public SensorModelList getSensorModels();

  /**
   * Retrieves the SensorProcess with the given id from the WattDepot Server.
   * 
   * @param id
   *          The SensorProcess's id.
   * @return the SensorProcess with the given id or null.
   * @exception IdNotFoundException
   *              if the given id is not a SensorProcess's id.
   */
  public SensorProcess getSensorProcess(String id) throws IdNotFoundException;

  /**
   * @return The defined SensorProcesses.
   */
  public SensorProcessList getSensorProcesses();

  /**
   * @return The defined Sensors.
   */
  public SensorList getSensors();

  /**
   * @param depository
   *          The Depository storing the measurements.
   * @param sensor
   *          The sensor making the measurements.
   * @param timestamp
   *          The time for the measured value.
   * @return The Value 'measured' at the given time, most likely an interpolated
   *         value.
   * @throws NoMeasurementException
   *           if there aren't any measurements around the timestamp.
   */
  public Double getValue(Depository depository, Sensor sensor, Date timestamp)
      throws NoMeasurementException;

  /**
   * @param depository
   *          The Depository storing the measurements.
   * @param sensor
   *          The sensor making the measurements.
   * @param start
   *          The start of the period.
   * @param end
   *          The end of the period.
   * @return The value measured the difference between the end value and the
   *         start value.
   * @throws NoMeasurementException
   *           if there are no measurements around the start or end time.
   */
  public Double getValue(Depository depository, Sensor sensor, Date start, Date end)
      throws NoMeasurementException;

  /**
   * @param depository
   *          The Depository storing the measurements.
   * @param sensor
   *          The Sensor making the measurements.
   * @param start
   *          The start of the interval.
   * @param end
   *          The end of the interval
   * @param gapSeconds
   *          The maximum number of seconds that measurements need to be within
   *          the start and end.
   * @return The value measured the difference between the end value and the
   *         start value.
   * @throws NoMeasurementException
   *           if there are no measurements around the start or end time.
   * @throws MeasurementGapException
   *           if the measurements around start or end are too far apart.
   */
  public Double getValue(Depository depository, Sensor sensor, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException;

  /**
   * @param depository
   *          The Depository storing the measurements.
   * @param sensor
   *          The Sensor making the measurements.
   * @param timestamp
   *          The time of the value.
   * @param gapSeconds
   *          The maximum number of seconds that measurements need to be within
   *          the start and end.
   * @return The Value 'measured' at the given time, most likely an interpolated
   *         value.
   * @throws NoMeasurementException
   *           If there aren't any measurements around the time.
   * @throws MeasurementGapException
   *           if the measurements around timestamp are too far apart.
   */
  public Double getValue(Depository depository, Sensor sensor, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException;

  /**
   * Stores the given Depository in the WattDepot Server.
   * 
   * @param depository
   *          the Depository.
   */
  public void putDepository(Depository depository);

  /**
   * Stores the given Location in the WattDepot Server.
   * 
   * @param loc
   *          the Location.
   */
  public void putLocation(Location loc);

  /**
   * @param depository
   *          The Depository to store the Measurement.
   * @param measurement
   *          The Measurement to store.
   * @throws MeasurementTypeException
   *           if the type of the measurement doesn't match the type of the
   *           depository.
   */
  public void putMeasurement(Depository depository, Measurement measurement)
      throws MeasurementTypeException;

  /**
   * Stores the given MeasurementType in the WattDepot Server.
   * 
   * @param type
   *          the MeasurementType.
   */
  public void putMeasurementType(MeasurementType type);

  /**
   * Stores the given Sensor in the WattDepot Server.
   * 
   * @param sensor
   *          the Sensor.
   */
  public void putSensor(Sensor sensor);

  /**
   * Stores the given SensorGroup in the WattDepot Server.
   * 
   * @param group
   *          the SensorGroup.
   */
  public void putSensorGroup(SensorGroup group);

  /**
   * Stores the given SensorModel in the WattDepot Server.
   * 
   * @param model
   *          the SensorModel.
   */
  public void putSensorModel(SensorModel model);

  /**
   * Stores the given SensorProcess in the WattDepot Server.
   * 
   * @param process
   *          the SensorProcess.
   */
  public void putSensorProcess(SensorProcess process);

  /**
   * Updates the given Depository in the WattDepot Server.
   * 
   * @param depository
   *          The Depository to update.
   */
  public void updateDepository(Depository depository);

  /**
   * Updates the given Location in the WattDepot Server.
   * 
   * @param location
   *          the Location to update.
   */
  public void updateLocation(Location location);

  /**
   * Updates the given MeasurementType in the WattDepot Server.
   * 
   * @param type
   *          the MeasurementType to update.
   */
  public void updateMeasurementType(MeasurementType type);

  /**
   * Updates the given Sensor in the WattDepot Server.
   * 
   * @param sensor
   *          The Sensor to update.
   */
  public void updateSensor(Sensor sensor);

  /**
   * Updates the given SensorGroup in the WattDepot Server.
   * 
   * @param group
   *          The SensorGroup to update.
   */
  public void updateSensorGroup(SensorGroup group);

  /**
   * Updates the given SensorModel in the WattDepot Server.
   * 
   * @param model
   *          the SensorModel to update.
   */
  public void updateSensorModel(SensorModel model);

  /**
   * Updates the given SensorProcess in the WattDepot Server.
   * 
   * @param process
   *          The SensorProcess to update.
   */
  public void updateSensorProcess(SensorProcess process);
}
