/**
 * MeasurementSource.java This file is part of WattDepot 3.
 *
 * Copyright (C) 2013  Cam Moore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wattdepot3.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

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
    Unit<?> watthour = SI.WATT.times(NonSI.HOUR);
    System.out.println(watthour);
    Measurement m = new Measurement(s, new Timestamp(now.getTime()), Math.random() * 1000, watthour);
    client.put(m);
  }

}
