/**
 * API.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.restlet;

/**
 * API - Defines the Strings that make up the HTTP API.
 *
 * @author Cam Moore
 *
 */
public class API {

  /** The base URI used for all WattDepot URIs. */
  public static final String BASE_URI = "wattdepot/";
  
  /** The URI to get to the administration UI. */
  public static final String ADMIN_URI = "admin/";
  
  /** The URI to get all the Depositories. */
  public static final String DEPOSITORIES_URI = "depositories/";
  
  /** The URI to manipulate a given Depository. */
  public static final String DEPOSITORY_URI = "depository/";
  
  /** The URI to manipulate a given Location. */
  public static final String LOCATION_URI = "location/";
  
  /** The URI to get all the Locations. */
  public static final String LOCATIONS_URI = "locations/";
  
  /** The URI to manipulate a given Measurement. */
  public static final String MEASUREMENT_URI = "measurement/";

  /** The URI to get all the Measurements. */
  public static final String MEASUREMENTS_URI = "measurements/";
  
  /** The URI to manipulate a given MeasurementType. */
  public static final String MEASUREMENT_TYPE_URI = "measurementtype/";

  /** The URI to get all the MeasurementTypes. */
  public static final String MEASUREMENT_TYPES_URI = "measurementtypes/";
  
  /** The URI to manipulate a given Sensor. */
  public static final String SENSOR_URI = "sensor/";

  /** The URI to get all Sensors. */
  public static final String SENSORS_URI = "sensors/";
  
  /** The URI to manipulate a given SensorGroup. */
  public static final String SENSOR_GROUP_URI = "sensorgroup/";

  /** The URI to get all SensorGroups. */
  public static final String SENSOR_GROUPS_URI = "sensorgroups/";

  /** The URI to manipulate a given SensorModel. */
  public static final String SENSOR_MODEL_URI = "sensormodel/";

  /** The URI to get all SensorModels. */
  public static final String SENSOR_MODELS_URI = "sensormodels/";

  /** The URI to manipulate a given SensorProcess. */
  public static final String SENSOR_PROCESS_URI = "sensorprocess/";

  /** The URI to get all SensorProcesses. */
  public static final String SENSOR_PROCESSES_URI = "sensorprocesses/";
  
  /** The URI for getting a 'measured' value. */
  public static final String VALUE_URI = "value/";
}
