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

}
