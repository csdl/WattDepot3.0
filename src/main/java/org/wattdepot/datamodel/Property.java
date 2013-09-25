/**
 * 
 */
package org.wattdepot.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author Cam Moore
 *
 */
@Entity
public class Property {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // for the database
  private String key;
  private String value;
  /**
   * Hide the default constructor.
   */
  protected Property() {
    id = 0L;
  }

  /**
   * @param key The key.
   * @param value The value.
   */
  public Property(String key, String value) {
    super();
    this.key = key;
    this.value = value;
  }
  
  /* (non-Javadoc)
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

  /* (non-Javadoc)
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Property [id=" + id + ", key=" + key + ", value=" + value + "]";
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }
  /**
   * @param key the key to set
   */
  public void setKey(String key) {
    this.key = key;
  }
  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }
  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }
  
  
}
