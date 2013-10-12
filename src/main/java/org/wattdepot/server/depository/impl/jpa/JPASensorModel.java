/**
 * JPASensorModel.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wattdepot.server.datamodel.SensorModel;

/**
 * JPASensorModel persistent version of SensorModel using JPA.
 * 
 * @author Cam Moore
 * 
 */
@Entity
public class JPASensorModel extends SensorModel {
  /** The database id. */
  private Long id;

  /** Hide the default constructor. */
  protected JPASensorModel() {
    super();
  }

  /**
   * @param id2
   *          The unique String id.
   * @param protocol
   *          The protocol.
   * @param type
   *          The type.
   * @param version
   *          The version.
   */
  public JPASensorModel(String id2, String protocol, String type, String version) {
    super(id2, protocol, type, version);
  }
  
  /**
   * @param sm The SensorModel to clone.
   */
  protected JPASensorModel(SensorModel sm) {
    super(sm.id(), sm.getProtocol(), sm.getType(), sm.getVersion());
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
