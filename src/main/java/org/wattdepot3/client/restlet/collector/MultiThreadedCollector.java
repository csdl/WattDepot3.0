/**
 * MultiThreadedCollector.java created on Nov 21, 2013 by Cam Moore.
 */
package org.wattdepot3.client.restlet.collector;

import java.util.TimerTask;

import org.apache.commons.validator.UrlValidator;
import org.wattdepot3.client.restlet.WattDepotClient;
import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.datamodel.SensorProcess;
import org.wattdepot3.exception.BadCredentialException;
import org.wattdepot3.exception.BadSensorUriException;
import org.wattdepot3.exception.IdNotFoundException;
import org.wattdepot3.util.Slug;

/**
 * MultiThreadedCollector - Abstract base class for all Multi-Threaded
 * Collectors.
 * 
 * @author Cam Moore
 * 
 */
public abstract class MultiThreadedCollector extends TimerTask {

  /** Flag for debugging messages. */
  protected boolean debug;
  /** The metadata about the collector. */
  protected SensorProcess metaData;

  /** The client used to communicate with the WattDepot server. */
  protected WattDepotClient client;

  /**
   * Initializes the MultiThreadedCollector.
   * 
   * @param serverUri
   *          The URI for the WattDepot server.
   * @param username
   *          The name of a user defined in the WattDepot server.
   * @param password
   *          The password for the user.
   * @param processId
   *          The SensorProcessId used to initialize this collector.
   * @param debug
   *          flag for debugging messages.
   * @throws BadCredentialException
   *           if the user or password don't match the credentials in WattDepot.
   * @throws IdNotFoundException
   *           if the processId is not defined.
   * @throws BadSensorUriException
   *           if the Sensor's URI isn't valid.
   */
  public MultiThreadedCollector(String serverUri, String username, String password,
      String processId, boolean debug) throws BadCredentialException, IdNotFoundException,
      BadSensorUriException {
    this.client = new WattDepotClient(serverUri, username, password);
    this.debug = debug;
    this.metaData = client.getSensorProcess(processId);
    validate();
  }

  /**
   * @param serverUri
   *          The URI for the WattDepot server.
   * @param username
   *          The name of a user defined in the WattDepot server.
   * @param password
   *          The password for the user.
   * @param sensor
   *          The Sensor to poll.
   * @param pollingInterval
   *          The polling interval in seconds.
   * @param depository
   *          The Depository to store the measurements.
   * @param debug
   *          flag for debugging messages.
   * @throws BadCredentialException
   *           if the user or password don't match the credentials in WattDepot.
   * @throws BadSensorUriException
   *           if the Sensor's URI isn't valid.
   */
  public MultiThreadedCollector(String serverUri, String username, String password, Sensor sensor,
      Long pollingInterval, Depository depository, boolean debug) throws BadCredentialException,
      BadSensorUriException {
    this.client = new WattDepotClient(serverUri, username, password);
    this.debug = debug;
    this.metaData = new SensorProcess(Slug.slugify(sensor.getId() + " " + pollingInterval + " "
        + depository.getName()), sensor, pollingInterval, depository.getName(), null);
    client.putSensorProcess(metaData);
    validate();
  }

  /**
   * @throws BadSensorUriException
   *           if the Sensor's URI isn't valid.
   */
  private void validate() throws BadSensorUriException {
    Sensor s = metaData.getSensor();
    String[] schemes = { "http", "https" };
    UrlValidator urlValidator = new UrlValidator(schemes);
    if (!urlValidator.isValid(s.getUri())) {
      throw new BadSensorUriException(s.getUri() + " is not a valid URI.");
    }
  }
}
