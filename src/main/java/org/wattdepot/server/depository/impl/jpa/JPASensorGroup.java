/**
 * JPASensorGroup.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wattdepot.server.datamodel.SensorGroup;

/**
 * JPASensorGroup persistent version of SensorGroup using JPA.
 *
 * @author Cam Moore
 *
 */
@Entity
public class JPASensorGroup extends SensorGroup {
  /** The database id. */
  private Long id;

  /**
   * Hide the default constructor.
   */
  protected JPASensorGroup() {
    super();
  }
  /**
   * @param id2 The unique String id.
   */
  public JPASensorGroup(String id2) {
    super(id2);
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
}
