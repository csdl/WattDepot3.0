/**
 * MeasurementType.java created on Nov 18, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import javax.measure.unit.Unit;

import org.wattdepot3.util.Slug;

/**
 * MeasurementType - Defines the type of a Measurement. This class includes a
 * human readable name, slug, and JScience Unit<?>.
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementType {
  private String name;
  private String slug;
  private Unit<?> unit;
  private String units;

  /**
   * Default constructor.
   */
  public MeasurementType() {

  }

  /**
   * Creates a new MeasurementType.
   * 
   * @param name
   *          the name of the type.
   * @param unit
   *          the units of measurement.
   */
  public MeasurementType(String name, Unit<?> unit) {
    this.name = name;
    this.slug = Slug.slugify(name);
    this.unit = unit;
    this.units = this.unit.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MeasurementType other = (MeasurementType) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    }
    else if (!name.equals(other.name)) {
      return false;
    }
    if (slug == null) {
      if (other.slug != null) {
        return false;
      }
    }
    else if (!slug.equals(other.slug)) {
      return false;
    }
    if (units == null) {
      if (other.units != null) {
        return false;
      }
    }
    else if (!units.equals(other.units)) {
      return false;
    }
    return true;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the slug
   */
  public String getSlug() {
    return slug;
  }

  /**
   * @return the units
   */
  public String getUnits() {
    return units;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((slug == null) ? 0 : slug.hashCode());
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    return result;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param slug
   *          the slug to set
   */
  public void setSlug(String slug) {
    this.slug = slug;
  }

  /**
   * @param units
   *          the units to set
   */
  public void setUnits(String units) {
    this.units = units;
    this.unit = Unit.valueOf(units);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "MeasurementType [name=" + name + ", slug=" + slug + ", units=" + units + "]";
  }

  /**
   * @return The Unit<?> associated with this MeasurementType.
   */
  public Unit<?> unit() {
    return unit;
  }
}
