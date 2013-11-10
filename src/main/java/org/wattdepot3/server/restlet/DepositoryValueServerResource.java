/**
 * DepositoryValueServerResource.java created on Oct 22, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.util.Date;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.MeasuredValue;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.NoMeasurementException;
import org.wattdepot3.restlet.DepositoryValueResource;
import org.wattdepot3.server.depository.impl.hibernate.DepositoryImpl;

/**
 * DepositoryValueServerResource - ServerResouce that handles the GET
 * /wattdepot/{group_id}/depository/{depository_id}/value/ response.
 * 
 * @author Cam Moore
 * 
 */
public class DepositoryValueServerResource extends WattDepotServerResource implements
    DepositoryValueResource {
  private String depositoryId;
  private String sensorId;
  private String start;
  private String end;
  private String timestamp;

  /*
   * (non-Javadoc)
   * 
   * @see org.restlet.resource.Resource#doInit()
   */
  @Override
  protected void doInit() throws ResourceException {
    super.doInit();
    this.sensorId = getQuery().getValues("sensor");
    this.start = getQuery().getValues("start");
    this.end = getQuery().getValues("end");
    this.timestamp = getQuery().getValues("timestamp");
    this.groupId = getAttribute("group_id");
    this.depositoryId = getAttribute("depository_id");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.wattdepot3.restlet.DepositoryValueResource#retrieve()
   */
  @Override
  public MeasuredValue retrieve() {
    System.out.println("GET /wattdepot/{" + groupId + "}/depository/{" + depositoryId
        + "}/value/?sensor={" + sensorId + "}&start={" + start + "}&end={" + end + "}&timestamp={"
        + timestamp + "}");
    try {
      DepositoryImpl deposit = (DepositoryImpl) depot.getWattDeposiory(depositoryId, groupId);
      if (deposit != null) {
        Sensor sensor = depot.getSensor(sensorId, groupId);
        if (sensor != null) {
          Double value = null;
          if (start != null && end != null) {
            Date startDate = new Date(Long.parseLong(start));
            Date endDate = new Date(Long.parseLong(end));
            value = deposit.getValue(sensor, startDate, endDate);
          }
          else if (timestamp != null) {
            Date time = new Date(Long.parseLong(timestamp));
            value = deposit.getValue(sensor, time);
          }
          MeasuredValue val = new MeasuredValue(sensorId, value, deposit.getMeasurementType());
          return val;
        }
        else {
          setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Could not find sensor " + sensorId);
        }
      }
      else {
        setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Could not find depository " + depositoryId);
      }
    }
    catch (MissMatchedOwnerException e) {
      setStatus(Status.CLIENT_ERROR_CONFLICT, e.getMessage());
    }
    catch (NoMeasurementException e1) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, e1.getMessage());
    }
    return null;
  }
}
