/**
 * MeasurementImpl.java created on Nov 7, 2013 by Cam Moore.
 */
package org.wattdepot3.server.depository.impl.hibernate;

import java.sql.Timestamp;

import org.wattdepot3.datamodel.Depository;
import org.wattdepot3.datamodel.Measurement;
import org.wattdepot3.datamodel.Sensor;

/**
 * MeasurementImpl - Hibernate persistant version of Measurement. It is 'stored'
 * in a Depository.
 * 
 * @author Cam Moore
 * 
 */
public class MeasurementImpl extends Measurement {
  /** The depository storing this measurement. */
  private Depository depository;

  /**
   * @return the depository
   */
  public Depository getDepository() {
    return depository;
  }

  /**
   * @param depository the depository to set
   */
  public void setDepository(Depository depository) {
    this.depository = depository;
  }

  /**
   * Default constructor.
   */
  public MeasurementImpl() {
    super();
  }

  /**
   * @param sensor
   *          The sensor that made the measurement.
   * @param timestamp
   *          The time of the measurement.
   * @param value
   *          The value measured.
   * @param measurementType
   *          The type of the measurement.
   */
  public MeasurementImpl(Sensor sensor, Timestamp timestamp, Double value, String measurementType) {
    super(sensor, timestamp, value, measurementType);
  }

  /**
   * Creates a new MeasurementImpl from the Measurement.
   * 
   * @param meas
   *          the Measurement to clone.
   */
  public MeasurementImpl(Measurement meas) {
    super(meas.getSensor(), meas.getTimestamp(), meas.getValue(), meas.getMeasurementType());
  }


  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((depository == null) ? 0 : depository.hashCode());
    return result;
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
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MeasurementImpl other = (MeasurementImpl) obj;
    if (depository == null) {
      if (other.depository != null) {
        return false;
      }
    }
    else if (!depository.equals(other.depository)) {
      return false;
    }
    return true;
  }

}
