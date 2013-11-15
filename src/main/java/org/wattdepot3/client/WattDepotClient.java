/**
 * WattDepotClient.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import java.util.Date;
import java.util.List;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.DepositoryList;
import org.wattdepot3.datamodel.Location;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorGroup;
import org.wattdepot3.datamodel.SensorModel;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.exception.BadCredentialException;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.exception.MeasurementGapException;
import org.wattdepot3.exception.MeasurementTypeException;
import org.wattdepot3.exception.NoMeasurementException;
import org.wattdepot3.restlet.API;
import org.wattdepot3.restlet.DepositoriesResource;
import org.wattdepot3.restlet.DepositoryResource;

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

  /**
   * Creates a ClientResource for the given request. Calling code MUST release
   * the ClientResource when finished.
   * 
   * @param requestString
   *          A String, the request portion of the WattDepot HTTP API, such as "
   * @return The client resource.
   */
  public ClientResource makeClient(String requestString) {
    Reference reference = new Reference(this.wattDepotUri + requestString);
    ClientResource client = new ClientResource(reference);
    client.setChallengeResponse(authentication);
    return client;
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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    Depository ret = resource.retrieve();
    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getLocation(java.lang.String)
   */
  @Override
  public Location getLocation(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getLocations()
   */
  @Override
  public List<Location> getLocations() {
    // TODO Auto-generated method stub
    return null;
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
  public List<Measurement> getMeasurements(Depository depository, Sensor sensor, Date start,
      Date end) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensor(java.lang.String)
   */
  @Override
  public Sensor getSensor(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorGroups()
   */
  @Override
  public List<SensorGroup> getSensorGroups() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getSensorModel(java.lang.String)
   */
  @Override
  public SensorModel getSensorModel(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorModels()
   */
  @Override
  public List<SensorModel> getSensorModels() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.wattdepot3.client.WattDepotInterface#getSensorProcess(java.lang.String)
   */
  @Override
  public SensorProcess getSensorProcess(String id) throws IdNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensorProcesses()
   */
  @Override
  public List<SensorProcess> getSensorProcesses() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.client.WattDepotInterface#getSensors()
   */
  @Override
  public List<Sensor> getSensors() {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
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
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

  }

}
