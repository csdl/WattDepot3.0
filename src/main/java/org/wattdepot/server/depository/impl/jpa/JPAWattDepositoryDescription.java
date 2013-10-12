/**
 * JPAWattDepositoryDescription.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * JPAWattDepositoryDescription persists the name and mesurementType for
 * WattDepositories.
 * 
 * @author Cam Moore
 * 
 */
@Entity
public class JPAWattDepositoryDescription {

  @Id
  @GeneratedValue
  private Long id;
  private String idStr;
  private String name;
  private String measurementType;

  /**
   * Hide the default constructor.
   */
  protected JPAWattDepositoryDescription() {

  }

  /**
   * Creates a new JPAWattDepositoryDescription.
   * @param id
   *          The unique id.
   * @param name
   *          The name.
   * @param measurementType
   *          the measurement type.
   */
  public JPAWattDepositoryDescription(String id, String name, String measurementType) {
    this.idStr = id;
    this.name = name;
    this.measurementType = measurementType;
  }

  /**
   * @return The id String for the Despository.
   */
  public String id() {
    return idStr;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the measurementType
   */
  public String getMeasurementType() {
    return measurementType;
  }

}
