/**
 * InstanceFactory.java created on Nov 20, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.measure.unit.Unit;
import javax.xml.datatype.DatatypeConfigurationException;

import org.wattdepot3.util.DateConvert;
import org.wattdepot3.util.UnitsHelper;

/**
 * InstanceFactory - Utility class that has static methods for creating
 * datamodel instances for testing.
 * 
 * @author Cam Moore
 * 
 */
public class InstanceFactory {

  /**
   * @return A Depository instance for testing.
   */
  public static Depository getDepository() {
    return new Depository("test_depository_id", getMeasurementType(), getUserGroup());
  }

  /**
   * @return A Location instance for testing.
   */
  public static Location getLocation() {
    return new Location("test_location_id", new Double(21.294642), new Double(-157.812727),
        new Double(30), "Hale Aloha Ilima residence hall 6th floor", getUserGroup());
  }

  /**
   * @return A Measurement instance for testing.
   */
  public static Measurement getMeasurementOne() {
    try {
      Date measTime = DateConvert.parseCalStringToDate("2013-11-20T14:35:27.925-1000");
      Double value = 100.0;
      return new Measurement(getSensor(), measTime, value, getMeasurementType().unit());
    }
    catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (DatatypeConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @return A Measurement instance for testing.
   */
  public static Measurement getMeasurementThree() {
    try {
      Date measTime = DateConvert.parseCalStringToDate("2013-11-20T14:45:37.925-1000");
      Double value = 100.0;
      return new Measurement(getSensor(), measTime, value, getMeasurementType().unit());
    }
    catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (DatatypeConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @return A Measurement instance for testing.
   */
  public static Measurement getMeasurementTwo() {
    try {
      Date measTime = DateConvert.parseCalStringToDate("2013-11-20T14:35:37.925-1000");
      Double value = 100.0;
      return new Measurement(getSensor(), measTime, value, getMeasurementType().unit());
    }
    catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (DatatypeConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  /**
   * @return A MeasurementType instance for testing.
   */
  public static MeasurementType getMeasurementType() {
    Unit<?> unit = UnitsHelper.quantities.get("Flow Rate (gal/s)");
    return new MeasurementType("Test MeasurementType Name", unit);
  }

  /**
   * @return A Property instance for testing.
   */
  public static Property getProperty() {
    return new Property("test_key", "test_value");
  }

  /**
   * @return A Sensor instance for testing.
   */
  public static Sensor getSensor() {
    return new Sensor("test_sensor_id", "test_sensor_uri", getLocation(), getSensorModel(),
        getUserGroup());
  }

  /**
   * @return A SensorGroup instance for testing.
   */
  public static SensorGroup getSensorGroup() {
    Set<Sensor> sensors = new HashSet<Sensor>();
    sensors.add(getSensor());
    return new SensorGroup("test_sensor_group_id", sensors, getUserGroup());
  }

  /**
   * @return A SensorModel instance for testing.
   */
  public static SensorModel getSensorModel() {
    return new SensorModel("test_sensor_model_id", "test_model_protocol", "test_model_type",
        "test_model_version", getUserGroup());
  }

  /**
   * @return A SensorProcess instance for testing.
   */
  public static SensorProcess getSensorProcess() {
    return new SensorProcess("sensor_process_id", getSensor(), 10L, "test_depository_id",
        getUserGroup());
  }

  /**
   * @return A UserGroup instance for testing.
   */
  public static UserGroup getUserGroup() {
    Set<UserInfo> users = new HashSet<UserInfo>();
    users.add(getUserInfo());
    return new UserGroup("test_user_group_id", users);
  }

  /**
   * @return A UserInfo instance for testing.
   */
  public static UserInfo getUserInfo() {
    Set<Property> properties = new HashSet<Property>();
    properties.add(getProperty());
    return new UserInfo("test_user_id", "test_first_name", "test_last_name", "test_email@test.com",
        false, properties);
  }

  /**
   * @return A UserPassword instance for testing.
   */
  public static UserPassword getUserPassword() {
    return new UserPassword("test_user_id", "plain_text_password");
  }
}
