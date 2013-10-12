/**
 * TestSensorModel.java created on Oct 11, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * TestSensorModel - Test cases for the SensorModel class.
 *
 * @author Cam Moore
 *
 */
public class TestSensorModel {

  /**
   * Outputs the JSON.
   */
  @Test
  public void test() {
    SensorModel sm = new SensorModel("egauge1.0", "eGauge", "EGAUGE", "1.0" );
    System.out.println(sm.toJSON());
    fail("Not yet implemented");
  }

}
