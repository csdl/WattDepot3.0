/**
 * DepositoryValueServerResource.java created on Oct 22, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import org.restlet.resource.ResourceException;
import org.wattdepot3.datamodel.MeasuredValue;
import org.wattdepot3.restlet.DepositoryValueResource;

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

    // TODO Auto-generated method stub
    return null;
  }

}
