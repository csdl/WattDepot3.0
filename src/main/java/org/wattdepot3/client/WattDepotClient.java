/**
 * WattDepotClient.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.DepositoryList;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.LocationList;
import org.wattdepot3.datamodel.MeasuredValue;
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
import org.wattdepot3.exception.BadCredentialException;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MeasurementGapException;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.NoMeasurementException;
import org.wattdepot3.restlet.API;
import org.wattdepot3.restlet.DepositoriesResource;
import org.wattdepot3.restlet.DepositoryMeasurementResource;
import org.wattdepot3.restlet.DepositoryMeasurementsResource;
import org.wattdepot3.restlet.DepositoryResource;
import org.wattdepot3.restlet.DepositoryValueResource;
import org.wattdepot3.restlet.LocationResource;
import org.wattdepot3.restlet.LocationsResource;
import org.wattdepot3.restlet.MeasurementTypeResource;
import org.wattdepot3.restlet.MeasurementTypesResource;
import org.wattdepot3.restlet.SensorGroupResource;
import org.wattdepot3.restlet.SensorGroupsResource;
import org.wattdepot3.restlet.SensorModelResource;
import org.wattdepot3.restlet.SensorModelsResource;
import org.wattdepot3.restlet.SensorProcessResource;
import org.wattdepot3.restlet.SensorProcessesResource;
import org.wattdepot3.restlet.SensorResource;
import org.wattdepot3.restlet.SensorsResource;
import org.wattdepot3.util.DateConvert;

/**
 * WattDepotClient - high-level Java implementation that communicates with a
 * WattDepot3 server. It implements the WattDepotInterface that hides the HTTP
 * API.
 * 
 * 
 * @author Cam Moore
 * 
 */
public class WattDepotClient implements WattDepotInterface {

  private String wattDepotUri;
  /** The HTTP authentication approach. */
  private ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
  private ChallengeResponse authentication;
  private String groupId;

  /**
   * Creates a new WattDepotClient.
   * 
   * @param serverUri
   *          The URI of the WattDepot server (e.g.
   *          "http://server.wattdepot.org/")
   * @param username
   *          The name of the user. The user must be defined in the WattDepot
   *          server.
   * @param password
   *          The password for the user.
   * @throws BadCredentialException
   *           If the user or password don't match the credentials on the
   *           WattDepot server.
   */
  public WattDepotClient(String serverUri, String username, String password)
      throws BadCredentialException {
    this.authentication = new ChallengeResponse(this.scheme, username, password);
    if (serverUri == null) {
      throw new IllegalArgumentException("serverUri cannot be null");
    }
    if (!serverUri.endsWith("/")) {
      throw new IllegalArgumentException("serverUri must end with '/'");
    }
    this.wattDepotUri = serverUri + API.BASE_URI;

    ClientResource client = null;

    client = makeClient(API.ADMIN_URI);
    try {
      client.head();
      if (client.getLocationRef() != null) {
        String path = client.getLocationRef().getPath();
        path = path.substring(0, path.length() - 1);
        int lastSlash = path.lastIndexOf('/') + 1;
        groupId = path.substring(lastSlash);
      }
      else {
        groupId = "admin";
      }
      client.release();
    }
    catch (ResourceException e) {
      throw new BadCredentialException(e.getMessage() + " username and or password are not corect.");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteDepository(org.wattdepot3
   * .datamodel.Depository)
   */
  @Override
  public void deleteDepository(Depository depository) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI
        + depository.getName());
    DepositoryResource resource = client.wrap(DepositoryResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(depository + " is not stored in WattDepot.");
      }
    }
    finally {
      client.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteLocation(org.wattdepot3.
   * datamodel.Location)
   */
  @Override
  public void deleteLocation(Location location) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.LOCATION_URI + location.getId());
    LocationResource resource = client.wrap(LocationResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(location + " is not stored in WattDepot.");
      }
    }
    finally {
      client.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteMeasurement(org.wattdepot3
   * .datamodel.Depository, org.wattdepot3.datamodel.Measurement)
   */
  @Override
  public void deleteMeasurement(Depository depository, Measurement measurement)
      throws IdNotFoundException {
    throw new RuntimeException("Deletion of Measurements not implemented.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteMeasurementType(org.wattdepot3
   * .datamodel.MeasurementType)
   */
  @Override
  public void deleteMeasurementType(MeasurementType type) {
    ClientResource client = makeClient(API.MEASUREMENT_TYPE_URI + type.getSlug());
    MeasurementTypeResource resource = client.wrap(MeasurementTypeResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      e.printStackTrace();
    }
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteSensor(org.wattdepot3.datamodel
   * .Sensor)
   */
  @Override
  public void deleteSensor(Sensor sensor) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_URI + sensor.getId());
    SensorResource resource = client.wrap(SensorResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      e.printStackTrace();
    }
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteSensorGroup(org.wattdepot3
   * .datamodel.SensorGroup)
   */
  @Override
  public void deleteSensorGroup(SensorGroup group) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_GROUP_URI + group.getId());
    SensorGroupResource resource = client.wrap(SensorGroupResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(group + " is not stored in WattDepot.");
      }
    }
    finally {
      client.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteSensorModel(org.wattdepot3
   * .datamodel.SensorModel)
   */
  @Override
  public void deleteSensorModel(SensorModel model) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_MODEL_URI + model.getId());
    SensorModelResource resource = client.wrap(SensorModelResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(model + " is not stored in WattDepot.");
      }
    }
    finally {
      client.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#deleteSensorProcess(org.wattdepot3
   * .datamodel.SensorProcess)
   */
  @Override
  public void deleteSensorProcess(SensorProcess process) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_PROCESS_URI
        + process.getId());
    SensorProcessResource resource = client.wrap(SensorProcessResource.class);
    try {
      resource.remove();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(process + " is not stored in WattDepot.");
      }
    }
    finally {
      client.release();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getDepositories()
   */
  @Override
  public DepositoryList getDepositories() {
    ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORIES_URI);
    DepositoriesResource resource = client.wrap(DepositoriesResource.class);
    DepositoryList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getDepository(java.lang.String)
   */
  @Override
  public Depository getDepository(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI + id);
    DepositoryResource resource = client.wrap(DepositoryResource.class);
    Depository ret = null;
    try {
      ret = resource.retrieve();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known Depository id.");
      }
    }
    finally {
      client.release();
    }
    return ret;
  }

  /**
   * @return the groupId
   */
  public String getGroupId() {
    return groupId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getLocation(java.lang.String)
   */
  @Override
  public Location getLocation(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.LOCATION_URI + id);
    LocationResource resource = client.wrap(LocationResource.class);
    Location ret = null;
    try {
      ret = resource.retrieve();
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known Location id.");
      }
    }
    finally {
      client.release();
    }
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getLocations()
   */
  @Override
  public LocationList getLocations() {
    ClientResource client = makeClient(this.groupId + "/" + API.LOCATIONS_URI);
    LocationsResource resource = client.wrap(LocationsResource.class);
    LocationList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getMeasurements(org.wattdepot3
   * .datamodel.Depository, org.wattdepot3.datamodel.Sensor, java.util.Date,
   * java.util.Date)
   */
  @Override
  public MeasurementList getMeasurements(Depository depository, Sensor sensor, Date start, Date end) {
    try {
      ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI
          + depository.getName() + API.MEASUREMENTS_URI + "?sensor=" + sensor.getId() + "&start="
          + DateConvert.convertDate(start) + "&end=" + DateConvert.convertDate(end));
      DepositoryMeasurementsResource resource = client.wrap(DepositoryMeasurementsResource.class);
      MeasurementList ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getMeasurementType(java.lang.String
   * )
   */
  @Override
  public MeasurementType getMeasurementType(String id) throws IdNotFoundException {
    ClientResource client = makeClient(API.MEASUREMENT_TYPE_URI + id);
    MeasurementTypeResource resource = client.wrap(MeasurementTypeResource.class);
    try {
      MeasurementType ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known MeasurementType. ");
      }
      e.printStackTrace();
    }
    finally {
      if (client != null) {
        client.release();
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getMeasurementTypes()
   */
  @Override
  public MeasurementTypeList getMeasurementTypes() {
    ClientResource client = makeClient(API.MEASUREMENT_TYPES_URI);
    MeasurementTypesResource resource = client.wrap(MeasurementTypesResource.class);
    MeasurementTypeList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensor(java.lang.String)
   */
  @Override
  public Sensor getSensor(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_URI + id);
    SensorResource resource = client.wrap(SensorResource.class);
    try {
      Sensor ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known Sensor. ");
      }
      e.printStackTrace();
    }
    finally {
      if (client != null) {
        client.release();
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getSensorGroup(java.lang.String)
   */
  @Override
  public SensorGroup getSensorGroup(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_GROUP_URI + id);
    SensorGroupResource resource = client.wrap(SensorGroupResource.class);
    try {
      SensorGroup ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known SensorGroup. ");
      }
      e.printStackTrace();
    }
    finally {
      if (client != null) {
        client.release();
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorGroups()
   */
  @Override
  public SensorGroupList getSensorGroups() {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_GROUPS_URI);
    SensorGroupsResource resource = client.wrap(SensorGroupsResource.class);
    SensorGroupList ret = resource.retrieve();
    client.release();
    return ret;

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getSensorModel(java.lang.String)
   */
  @Override
  public SensorModel getSensorModel(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_MODEL_URI + id);
    SensorModelResource resource = client.wrap(SensorModelResource.class);
    try {
      SensorModel ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known SensorModel. ");
      }
      e.printStackTrace();
    }
    finally {
      if (client != null) {
        client.release();
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorModels()
   */
  @Override
  public SensorModelList getSensorModels() {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_MODELS_URI);
    SensorModelsResource resource = client.wrap(SensorModelsResource.class);
    SensorModelList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getSensorProcess(java.lang.String)
   */
  @Override
  public SensorProcess getSensorProcess(String id) throws IdNotFoundException {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_PROCESS_URI + id);
    SensorProcessResource resource = client.wrap(SensorProcessResource.class);
    try {
      SensorProcess ret = resource.retrieve();
      client.release();
      return ret;
    }
    catch (ResourceException e) {
      if (e.getStatus().equals(Status.CLIENT_ERROR_EXPECTATION_FAILED)) {
        throw new IdNotFoundException(id + " is not a known SensorProcess. ");
      }
      e.printStackTrace();
    }
    finally {
      if (client != null) {
        client.release();
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorProcesses()
   */
  @Override
  public SensorProcessList getSensorProcesses() {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_PROCESSES_URI);
    SensorProcessesResource resource = client.wrap(SensorProcessesResource.class);
    SensorProcessList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensors()
   */
  @Override
  public SensorList getSensors() {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSORS_URI);
    SensorsResource resource = client.wrap(SensorsResource.class);
    SensorList ret = resource.retrieve();
    client.release();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getValue(org.wattdepot3.datamodel
   * .Depository, org.wattdepot3.datamodel.Sensor, java.util.Date)
   */
  @Override
  public Double getValue(Depository depository, Sensor sensor, Date timestamp)
      throws NoMeasurementException {
    ClientResource client = null;
    try {
      client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI + depository.getName() + "/"
          + API.VALUE_URI + "?sensor=" + sensor.getId() + "&timestamp="
          + DateConvert.convertDate(timestamp));
      DepositoryValueResource resource = client.wrap(DepositoryValueResource.class);
      MeasuredValue ret = resource.retrieve();
      client.release();
      return ret.getValue();
    }
    catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getValue(org.wattdepot3.datamodel
   * .Depository, org.wattdepot3.datamodel.Sensor, java.util.Date,
   * java.util.Date)
   */
  @Override
  public Double getValue(Depository depository, Sensor sensor, Date start, Date end)
      throws NoMeasurementException {
    ClientResource client = null;
    try {
      client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI + depository.getName() + "/"
          + API.VALUE_URI + "?sensor=" + sensor.getId() + "&start="
          + DateConvert.convertDate(start) + "&end=" + DateConvert.convertDate(end));
      DepositoryValueResource resource = client.wrap(DepositoryValueResource.class);
      MeasuredValue ret = resource.retrieve();
      client.release();
      return ret.getValue();
    }
    catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getValue(org.wattdepot3.datamodel
   * .Depository, org.wattdepot3.datamodel.Sensor, java.util.Date,
   * java.util.Date, java.lang.Long)
   */
  @Override
  public Double getValue(Depository depository, Sensor sensor, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    ClientResource client = null;
    try {
      client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI + depository.getName() + "/"
          + API.VALUE_URI + "?sensor=" + sensor.getId() + "&start="
          + DateConvert.convertDate(start) + "&end=" + DateConvert.convertDate(end) + "&gap="
          + gapSeconds);
      DepositoryValueResource resource = client.wrap(DepositoryValueResource.class);
      MeasuredValue ret = resource.retrieve();
      client.release();
      return ret.getValue();
    }
    catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getValue(org.wattdepot3.datamodel
   * .Depository, org.wattdepot3.datamodel.Sensor, java.util.Date,
   * java.lang.Long)
   */
  @Override
  public Double getValue(Depository depository, Sensor sensor, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    ClientResource client = null;
    try {
      client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI + depository.getName() + "/"
          + API.VALUE_URI + "?sensor=" + sensor.getId() + "&timestamp="
          + DateConvert.convertDate(timestamp) + "&gap=" + gapSeconds);
      DepositoryValueResource resource = client.wrap(DepositoryValueResource.class);
      MeasuredValue ret = resource.retrieve();
      client.release();
      return ret.getValue();
    }
    catch (DatatypeConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @return the wattDepotUri
   */
  public String getWattDepotUri() {
    return wattDepotUri;
  }

  /**
   * Creates a ClientResource for the given request. Calling code MUST release
   * the ClientResource when finished.
   * 
   * @param requestString
   *          A String, the request portion of the WattDepot HTTP API, such as "
   * @return The client resource.
   */
  public ClientResource makeClient(String requestString) {
    System.out.println(requestString);
    Reference reference = new Reference(this.wattDepotUri + requestString);
    ClientResource client = new ClientResource(reference);
    client.setChallengeResponse(authentication);
    return client;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putDepository(org.wattdepot3.datamodel
   * .Depository)
   */
  @Override
  public void putDepository(Depository depository) {
    ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI
        + depository.getName());
    DepositoryResource resource = client.wrap(DepositoryResource.class);
    resource.store(depository);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putLocation(org.wattdepot3.datamodel
   * .Location)
   */
  @Override
  public void putLocation(Location loc) {
    ClientResource client = makeClient(this.groupId + "/" + API.LOCATION_URI + loc.getId());
    LocationResource resource = client.wrap(LocationResource.class);
    resource.store(loc);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putMeasurement(org.wattdepot3.
   * datamodel.Depository, org.wattdepot3.datamodel.Measurement)
   */
  @Override
  public void putMeasurement(Depository depository, Measurement measurement)
      throws MeasurementTypeException {
    if (!depository.getMeasurementType().getUnits().equals(measurement.getMeasurementType())) {
      throw new MeasurementTypeException("Depository " + depository.getName() + " stores "
          + depository.getMeasurementType() + " not " + measurement.getMeasurementType());
    }
    ClientResource client = makeClient(this.groupId + "/" + API.DEPOSITORY_URI
        + depository.getName() + "/" + API.MEASUREMENT_URI);
    DepositoryMeasurementResource resource = client.wrap(DepositoryMeasurementResource.class);
    try {
      resource.store(measurement);
    }
    catch (ResourceException e) {
      e.printStackTrace();
    }
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putMeasurementType(org.wattdepot3
   * .datamodel.MeasurementType)
   */
  @Override
  public void putMeasurementType(MeasurementType type) {
    ClientResource client = makeClient(API.MEASUREMENT_TYPE_URI + type.getSlug());
    MeasurementTypeResource resource = client.wrap(MeasurementTypeResource.class);
    resource.store(type);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putSensor(org.wattdepot3.datamodel
   * .Sensor)
   */
  @Override
  public void putSensor(Sensor sensor) {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_URI + sensor.getId());
    SensorResource resource = client.wrap(SensorResource.class);
    resource.store(sensor);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putSensorGroup(org.wattdepot3.
   * datamodel.SensorGroup)
   */
  @Override
  public void putSensorGroup(SensorGroup group) {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_GROUP_URI + group.getId());
    SensorGroupResource resource = client.wrap(SensorGroupResource.class);
    resource.store(group);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putSensorModel(org.wattdepot3.
   * datamodel.SensorModel)
   */
  @Override
  public void putSensorModel(SensorModel model) {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_MODEL_URI + model.getId());
    SensorModelResource resource = client.wrap(SensorModelResource.class);
    resource.store(model);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#putSensorProcess(org.wattdepot3
   * .datamodel.SensorProcess)
   */
  @Override
  public void putSensorProcess(SensorProcess process) {
    ClientResource client = makeClient(this.groupId + "/" + API.SENSOR_PROCESS_URI
        + process.getId());
    SensorProcessResource resource = client.wrap(SensorProcessResource.class);
    resource.store(process);
    client.release();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateDepository(org.wattdepot3
   * .datamodel.Depository)
   */
  @Override
  public void updateDepository(Depository depository) {
    putDepository(depository);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateLocation(org.wattdepot3.
   * datamodel.Location)
   */
  @Override
  public void updateLocation(Location location) {
    putLocation(location);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateMeasurementType(org.wattdepot3
   * .datamodel.MeasurementType)
   */
  @Override
  public void updateMeasurementType(MeasurementType type) {
    putMeasurementType(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateSensor(org.wattdepot3.datamodel
   * .Sensor)
   */
  @Override
  public void updateSensor(Sensor sensor) {
    putSensor(sensor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateSensorGroup(org.wattdepot3
   * .datamodel.SensorGroup)
   */
  @Override
  public void updateSensorGroup(SensorGroup group) {
    putSensorGroup(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateSensorModel(org.wattdepot3
   * .datamodel.SensorModel)
   */
  @Override
  public void updateSensorModel(SensorModel model) {
    putSensorModel(model);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#updateSensorProcess(org.wattdepot3
   * .datamodel.SensorProcess)
   */
  @Override
  public void updateSensorProcess(SensorProcess process) {
    putSensorProcess(process);
  }

}
