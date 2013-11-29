/**
 * Property.java created on Oct 3, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

/**
 * Property - Key value pair.
 * 
 * @author Cam Moore
 */
public class Property {
  /** Unique database id. If necessary. */
  private Long id;
  /** The key. */
  private String key;
  /** The value. */
  private String value;

  /**
   * The default constructor.
   */
  public Property() {

  }

  /**
   * Creates a new Property with the given key and value.
   * 
   * @param key
   *          The key.
   * @param value
   *          The value.
   */
  public Property(String key, String value) {
    super();
    this.key = key;
    this.value = value;
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
    Property other = (Property) obj;
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    }
    else if (!key.equals(other.key)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    }
    else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
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
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * @param id the dbId to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param key
   *          the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Property [key=" + key + ", value=" + value + "]";
  }
  
}
