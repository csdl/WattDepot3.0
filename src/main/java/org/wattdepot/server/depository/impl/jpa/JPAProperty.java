/**
 * JPAProperty.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wattdepot.server.datamodel.Property;

/**
 * JPAProperty persistent version of Property using JPA.
 * 
 * @author Cam Moore
 * 
 */
@Entity
public class JPAProperty extends Property {
  /** The database id. */
  private Long id;

  /** default constructor. */
  protected JPAProperty() {
    super();
  }

  /**
   * @param key
   *          The key.
   * @param value
   *          The value.
   */
  protected JPAProperty(String key, String value) {
    super(key, value);
  }

  /**
   * @param p
   *          The Property to clone.
   */
  protected JPAProperty(Property p) {
    super(p.getKey(), p.getValue());
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
