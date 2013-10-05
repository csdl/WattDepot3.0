/**
 * JPAMeasurement.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.wattdepot.server.datamodel.Measurement;

/**
 * JPAMeasurement persistent version of Measurement using JPA.
 *
 * @author Cam Moore
 *
 */
@Entity
@Table(name = "Measurement")
public class JPAMeasurement extends Measurement {
  /** The database id. */
  private Long id;
  private String depository;

  /**
   * Hide the default constructor.
   */
  protected JPAMeasurement() {
    
  }
  /**
   * @param meas the Measurement to clone.
   * @param name The name of the depository storing the measurement.
   */
  public JPAMeasurement(Measurement meas, String name) {
    super(new JPASensor(meas.getSensor()), meas.getTimestamp(), meas.getValue(), meas.getMeasurementType());
    this.depository = name;
  }

  /**
   * @return the id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the depository
   */
  public String getDepository() {
    return depository;
  }

  /**
   * @param depository the depository to set
   */
  public void setDepository(String depository) {
    this.depository = depository;
  }
}
