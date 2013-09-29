/**
 * MeasurmentType.java created on Sep 29, 2013 by Cam Moore.
 */
package org.wattdepot.repository;

/**
 * Encapsulates the known MeasurmentTypes in the WattDepotRepostory.
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementType {
  private String measurementName;
  private Class<?> measurementClass;

  /**
   * Creates a new MeasurementType.
   * 
   * @param name
   *          The name of the table in the repository.
   * @param cls
   *          The class of the measurement.
   */
  public MeasurementType(String name, Class<?> cls) {
    this.measurementName = name;
    this.measurementClass = cls;
  }

  /**
   * @return the measurementName
   */
  public String getMeasurementName() {
    return measurementName;
  }

  /**
   * @return the measurementClass
   */
  public Class<?> getMeasurementClass() {
    return measurementClass;
  }

}
