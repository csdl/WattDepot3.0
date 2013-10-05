/**
 * JPAWattDepositoryDescription.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * JPAWattDepositoryDescription persists the name and mesurementType for WattDepositories.
 *
 * @author Cam Moore
 *
 */
@Entity
public class JPAWattDepositoryDescription {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String measurementType;
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
