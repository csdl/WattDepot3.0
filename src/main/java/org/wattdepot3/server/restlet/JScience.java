/**
 * JScience.java created on Nov 16, 2013 by Cam Moore.
 */
package org.wattdepot3.server.restlet;

import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Power;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.jscience.physics.amount.Amount;
import org.wattdepot3.util.UnitsHelper;
/**
 * JScience - Testing the JScience classes.
 * 
 * @author Cam Moore
 * 
 */
public class JScience {

  /**
   * @param args
   *          command line arguments.
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // Exact Measurements.
    Amount<Mass> m0 = Amount.valueOf(100, NonSI.POUND); // Integer representation.
    Amount<Mass> m1 = m0.times(33).divide(2); // Still an integer.
    Amount<ElectricCurrent> m2 = Amount.valueOf("234 mA").to(SI.MICRO(SI.AMPERE)); // Exact
                                                                             // conversion.
    System.out.println(m0);
    System.out.println(m1);
    System.out.println(m2);
    Unit<Mass> centigram = SI.CENTI(SI.GRAM);
    Unit<Length> kilometer = SI.KILO(SI.METER);
    Unit<Power> megawatt = SI.MEGA(SI.WATT);
    Unit<?> watthour = SI.WATT.times(NonSI.HOUR);
    
    System.out.println(centigram);
    System.out.println(kilometer);
    System.out.println(megawatt);
    System.out.println(watthour);
    
    Amount<?> energyReading = Amount.valueOf(1.23, watthour);
    System.out.println(energyReading);
    
    Amount<?> second = Amount.valueOf("123 " + watthour.toString());
    System.out.println(second);
    System.out.println(second.getUnit().getStandardUnit());
    System.out.println("Defined SI units: ");
    for (Unit<?> u : SI.getInstance().getUnits()) {
      System.out.println(u);
    }
    System.out.println("Defined NonSI units: ");
    for (Unit<?> u : NonSI.getInstance().getUnits()) {
      System.out.println(u);
    }
    
    System.out.println("WattDepot Measurement Types:");
    for (String key : UnitsHelper.quantities.keySet()) {
      System.out.println(key);
    }
  }

  
}
