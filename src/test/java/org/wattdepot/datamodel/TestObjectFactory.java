/**
 * 
 */
package org.wattdepot.datamodel;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

import org.wattdepot.util.tstamp.Tstamp;

/**
 * @author Cam Moore
 * 
 */
public class TestObjectFactory {
  public static final String ABBR1 = "kW";
  public static final String ABBR2 = "kWh";
  public static final BigDecimal ALT1 = new BigDecimal(100.1);
  public static final BigDecimal ALT2 = new BigDecimal(110.1);
  public static final String DES1 = "Description1";
  public static final String DES2 = "Description2";
  public static final String KEY1 = "some-key";
  public static final String KEY2 = "foo-key";
  public static final BigDecimal LAT1 = new BigDecimal(21.294642);
  public static final BigDecimal LAT2 = new BigDecimal(21.294645);
  public static final BigDecimal LON1 = new BigDecimal(-157.812727);
  public static final BigDecimal LON2 = new BigDecimal(-157.812730);
  public static final BigDecimal MEASVAL1 = new BigDecimal(1.0);
  public static final BigDecimal MEASVAL2 = new BigDecimal(2.0);
  public static final String NAME1 = "Kilowatt";
  public static final String NAME2 = "Kilowatt Hour";
  public static final String OWNER1 = "owner1";
  public static final String OWNER2 = "owner2";
  public static final Boolean PRIVATE1 = false;
  public static final Boolean PRIVATE2 = false;
  public static final String PROTO1 = "prot1";
  public static final String PROTO2 = "prot2";
  public static final String TIMESTAMP_STR1 = "2013-09-14T08:00:00.000-10:00";
  public static final String TIMESTAMP_STR2 = "2013-09-15T15:02:00.000-10:00";
  public static final String TYPE1 = "type1";
  public static final String TYPE2 = "type2";
  public static final String VAL1 = "some-value";
  public static final String VAL2 = "foo-value";
  public static final String VER1 = "ver1";
  public static final String VER2 = "ver2";
  
  
  private static AccessControl acc1, acc2;
  private static Location loc1, loc2;
  private static Measurement meas1, meas2;
  private static MeterModel mod1, mod2;
  private static Property prop1, prop2;
  private static Properties props1, props2;
  private static Unit unit1, unit2;
  private static XMLGregorianCalendar cal1, cal2;
  

  public static AccessControl buildAccessControl1() {
    if (acc1 == null) {
      acc1 = new AccessControl(OWNER1, PRIVATE1);
    }
    return acc1;
  }

  public static AccessControl buildAccessControl2() {
    if (acc2 == null) {
      acc2 = new AccessControl(OWNER2, PRIVATE2);
    }
    return acc2;
  }

  public static XMLGregorianCalendar buildCal1() {
    if (cal1 == null) {
      try {
        cal1 = Tstamp.makeTimestamp(TIMESTAMP_STR1);
      } catch (Exception e) {
        cal1 = Tstamp.makeTimestamp();
      }
    }
    return cal1;
  }

  public static XMLGregorianCalendar buildCal2() {
    if (cal2 == null) {
      try {
        cal2 = Tstamp.makeTimestamp(TIMESTAMP_STR1);
      } catch (Exception e) {
        cal2 = Tstamp.makeTimestamp();
      }
    }
    return cal2;
  }

  public static Location buildLocation1() {
    if (loc1 == null) {
      loc1 = new Location(LAT1, LON1, ALT1, DES1);
    }
    return loc1;
  }

  public static Location buildLocation2() {
    if (loc2 == null) {
      loc2 = new Location(LAT2, LON2, ALT2, DES2);
    }
    return loc2;
  }

  public static Measurement buildMeasurment1() {
    if (meas1 == null) {
      meas1 = new Measurement(buildCal1(), MEASVAL1, buildUnit1(), buildProperty1());
    }
    return meas1;
  }

  public static Measurement buildMeasurment2() {
    if (meas2 == null) {
      meas2 = new Measurement(buildCal2(), MEASVAL2, buildUnit2(), buildProperty2());
    }
    return meas2;
  }
    
  public static MeterModel buildMeterModel1() {
    if (mod1 == null) {
      mod1 = new MeterModel(PROTO1, TYPE1, VER1);
    }
    return mod1;
  }

  public static MeterModel buildMeterModel2() {
    if (mod2 == null) {
      mod2 = new MeterModel(PROTO2, TYPE2, VER2);
    }
    return mod2;
  }
  
  public static Property buildProperty1() {
    if (prop1 == null) {
      prop1 = new Property(KEY1, VAL1);
    }
    return prop1;
  }

  public static Property buildProperty2() {
    if (prop2 == null) {
      prop2 = new Property(KEY2, VAL2);
    }
    return prop2;
  }
  
  public static Properties buildProperties1() {
    if (props1 == null) {
      props1 = new Properties();
    }
    return props1;
  }

  public static Properties buildProperties2() {
    if (props2 == null) {
      props2 = new Properties();
    }
    return props2;
  }
  
  public static Unit buildUnit1() {
    if (unit1 == null) {
      unit1 = new Unit(NAME1, ABBR1);
    }
    return unit1;
  }
  
  public static Unit buildUnit2() {
    if (unit2 == null) {
      unit2 = new Unit(NAME2, ABBR2);
    }
    return unit2;
  }
}
