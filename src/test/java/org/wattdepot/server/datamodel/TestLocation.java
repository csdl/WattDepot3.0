/**
 * TestLocation.java created on Oct 10, 2013 by Cam Moore.
 */
package org.wattdepot.server.datamodel;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * TestLocation - test cases for the Location class.
 *
 * @author Cam Moore
 *
 */
public class TestLocation {


  /**
   * 
   */
  @Test
  public void test() {
    Location loc = new Location("loc1", new Double(21.294642), new Double(-157.812727), new Double(
        30), "Hale Aloha Ilima residence hall 6th floor");
    System.out.println(loc.toJSON());
    fail("Not yet implemented");
  }

}
