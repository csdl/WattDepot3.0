/**
 * DepositoryValueServerResource.java created on Oct 22, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import java.text.ParseException;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.MeasuredValue;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.exception.MeasurementGapException;
import org.wattdepot3.exception.MissMatchedOwnerException;
import org.wattdepot3.exception.NoMeasurementException;
import org.wattdepot3.restlet.DepositoryValueResource;
import org.wattdepot3.server.depository.impl.hibernate.DepositoryImpl;
import org.wattdepot3.util.DateConvert;

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
  private String gapSeconds;

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
    this.gapSeconds = getQuery().getValues("gap");
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
        + timestamp + "}&gap={" + gapSeconds + "}");
    try {
      DepositoryImpl deposit = (DepositoryImpl) depot.getWattDeposiory(depositoryId, groupId);
      if (deposit != null) {
        Sensor sensor = depot.getSensor(sensorId, groupId);
        if (sensor != null) {
          Double value = null;
          Date startDate = null;
          Date endDate = null;
          Date time = null;
          if (start != null && end != null) {
            startDate = DateConvert.parseCalStringToDate(start);
            endDate = DateConvert.parseCalStringToDate(end);
            if (gapSeconds != null) {
              value = deposit.getValue(sensor, startDate, endDate, Long.parseLong(gapSeconds));
            }
            else {
              value = deposit.getValue(sensor, startDate, endDate);
            }
          }
          else if (timestamp != null) {
            time = DateConvert.parseCalStringToDate(timestamp);
            if (gapSeconds != null) {
              value = deposit.getValue(sensor, time, Long.parseLong(gapSeconds));
            }
            else {
              value = deposit.getValue(sensor, time);
            }
          }
          MeasuredValue val = new MeasuredValue(sensorId, value, deposit.getMeasurementType());
          if (end != null) {
            val.setDate(endDate);
          }
          else if (time != null) {
            val.setDate(time);
          }
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
    catch (NumberFormatException e) {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
    }
    catch (MeasurementGapException e) {
      setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED, e.getMessage());
    }
    catch (ParseException e) {
      setStatus(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
      e.printStackTrace();
    }
    catch (DatatypeConfigurationException e) {
      setStatus(Status.SERVER_ERROR_INTERNAL, e.getMessage());
    }
    return null;
  }
}
