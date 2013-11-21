/**
 * UnitsHelper.java created on Nov 19, 2013 by Cam Moore.
 */
package org.wattdepot3.util;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;

/**
 * UnitsHelper - Utility class that helps build measurement types.
 * 
 * @author Cam Moore
 * 
 */
public class UnitsHelper {
  /** Holds the defined units. */
  public static final Map<String, Unit<?>> quantities = new HashMap<String, Unit<?>>();

  static {
    UnitFormat format = UnitFormat.getInstance();
    format.alias(SI.MICRO(Unit.ONE), "ppm");
    format.label(SI.MICRO(Unit.ONE), "ppm");    
    quantities.put(buildName("Power", SI.WATT), SI.WATT);
    quantities.put(buildName("Energy", SI.WATT.times(NonSI.HOUR)), SI.WATT.times(NonSI.HOUR));
    quantities.put(buildName("Frequency", SI.HERTZ), SI.HERTZ);
    quantities.put(buildName("Temperature", NonSI.FAHRENHEIT), NonSI.FAHRENHEIT);
    quantities.put(buildName("Temperature", SI.CELSIUS), SI.CELSIUS);
    quantities.put(buildName("Volume", NonSI.GALLON_LIQUID_US), NonSI.GALLON_LIQUID_US);
    quantities.put(buildName("Volume", NonSI.LITER), NonSI.LITER);
    quantities.put(buildName("Flow Rate", NonSI.GALLON_LIQUID_US.divide(SI.SECOND)),
        NonSI.GALLON_LIQUID_US.divide(SI.SECOND));
    quantities.put(buildName("Flow Rate", NonSI.LITER.divide(SI.SECOND)),
        NonSI.LITER.divide(SI.SECOND));
    quantities.put(buildName("Mass", SI.KILOGRAM), SI.KILOGRAM);
    quantities.put(buildName("Mass", NonSI.POUND), NonSI.POUND);
    quantities.put(buildName("Humidity", NonSI.PERCENT), NonSI.PERCENT);
    quantities.put(buildName("Concentration", SI.MICRO(Unit.ONE)), SI.MICRO(Unit.ONE));

  }

  /**
   * @param type
   *          The type of the unit, Energy, Power, Mass, etc.
   * @param unit
   *          The Unit<?>.
   * @return The name.
   */
  public static String buildName(String type, Unit<?> unit) {
    String s = unit.toString();
    String s1 = Normalizer.normalize(s, Normalizer.Form.NFKD);
    String regex = Pattern.quote("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    try {
      String s2 = new String(s1.replaceAll(regex, "").getBytes("ascii"), "ascii");
      s2 = s2.replace("?", "");
      return type + " (" + s2 + ")";
      
    }
    catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return type + " (" + s1 + ")";
  }

}
