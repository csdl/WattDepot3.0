/**
 * TestClient.java created on Nov 14, 2013 by Cam Moore.
 */
package org.wattdepot3.client;

import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.DepositoryList;
import org.wattdepot3.datamodel.Sensor;
import org.wattdepot3.exception.BadCredentialException;
import org.wattdepot3.exception.IdNotFoundException;

/**
 * TestClient - Simple test of the WattDepotClient.
 * 
 * @author Cam Moore
 * 
 */
public class TestWattDepotClient {

  /**
   * @param args
   *          The command line arguments <server URI> <username> <password>.
   * @throws BadCredentialException
   *           if there is a problem with the username and password.
   */
  public static void main(String[] args) throws BadCredentialException {
    if (args.length != 3) {
      System.err.println("Usage: java TestClient <server URI> <username> <password>");
      System.exit(-1);
    }

    WattDepotClient client = new WattDepotClient(args[0], args[1], args[2]);

    try {
      Depository dep = client.getDepository("test");
      System.out.println(dep);
    }
    catch (IdNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    DepositoryList depos = client.getDepositories();
    for (Depository d : depos.getDepositories()) {
      System.out.println(d.getOwner());
    }

    Depository depo1 = new Depository("test-energy", "energy", null);
    client.putDepository(depo1);
    depos = client.getDepositories();
    for (Depository d : depos.getDepositories()) {
      System.out.println(d.getName());
    }
    try {
      client.deleteDepository(depo1);
    }
    catch (IdNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    depos = client.getDepositories();
    for (Depository d : depos.getDepositories()) {
      System.out.println(d.getName());
    }
    
    try {
      Sensor s = client.getSensor("badSensor");
    }
    catch (IdNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
