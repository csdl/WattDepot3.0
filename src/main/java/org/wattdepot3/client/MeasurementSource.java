/**
 * MeasurementSource.java created on Nov 10, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;

/**
 * MeasurementSource - trying to send a measurement to the WattDepot repository.
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementSource {
  /**
   * @param args
   *          Command line arguments.
   * @throws IOException
   *           if there is a problem.
   * @throws ResourceException
   *           if there is a problem.
   */
  public static void main(String[] args) throws IOException, ResourceException {
    ClientResource client = new ClientResource(
        "http://localhost:8119/wattdepot/admin/depository/test/measurement/");
    ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC,
        "admin", "admin");
    client.setChallengeResponse(challengeResponse);
    Sensor s = new Sensor("sensor1", "uri1", null, null, null);
    Date now = new Date();
    System.out.println("Setting time to " + now.getTime());
    Measurement m = new Measurement(s, new Timestamp(now.getTime()), Math.random() * 1000,
        "energy");
    client.put(m);
  }

}
