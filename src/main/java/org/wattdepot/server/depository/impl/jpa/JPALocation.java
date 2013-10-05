/**
 * JPALocation.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wattdepot.server.datamodel.Location;

/**
 * JPALocation persistent version of Location using JPA.
 * 
 * @author Cam Moore
 * 
 */
@Entity
public class JPALocation extends Location {
  /** The database id. */
  private Long id;

  /** Default constructor. */
  protected JPALocation() {
    super();
  }

  /**
   * @param id2
   *          The unique string id.
   * @param latitude
   *          The latitude.
   * @param longitude
   *          The longitude.
   * @param altitude
   *          The altitude in meters MSL.
   * @param description
   *          The description.
   */
  public JPALocation(String id2, Double latitude, Double longitude, Double altitude,
      String description) {
    super(id2, latitude, longitude, altitude, description);
  }

  /**
   * @param location The Location to clone.
   */
  protected JPALocation(Location location) {
    super(location.getUniqueId(), location.getLatitude(), location.getLongitude(), location
        .getAltitude(), location.getDescription());
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
