/**
 * DepositoryList.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.util.ArrayList;

/**
 * DepositoryList - Attempt at getting a list across the wire.
 *
 * @author Cam Moore
 *
 */
public class DepositoryList {
  private ArrayList<Depository> depositories;
  
  /**
   * Default Constructor.
   */
  public DepositoryList() {
    depositories = new ArrayList<Depository>();
  }

  /**
   * @return the depositories
   */
  public ArrayList<Depository> getDepositories() {
    return depositories;
  }

  /**
   * @param depositories the depositories to set
   */
  public void setDepositories(ArrayList<Depository> depositories) {
    this.depositories = depositories;
  }
  
  
}
