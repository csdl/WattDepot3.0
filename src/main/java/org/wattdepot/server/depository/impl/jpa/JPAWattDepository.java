/**
 * JpaWattDepository.java created on Oct 4, 2013 by Cam Moore.
 */
package org.wattdepot.server.depository.impl.jpa;

import java.util.Date;
import java.util.List;

import org.wattdepot.server.datamodel.Measurement;
import org.wattdepot.server.datamodel.Sensor;
import org.wattdepot.server.depository.MeasurementGapException;
import org.wattdepot.server.depository.MeasurementTypeException;
import org.wattdepot.server.depository.NoMeasurementException;
import org.wattdepot.server.depository.WattDepository;


/**
 * JpaWattDepository JPA implementation of WattDepository uses hibernate.
 *
 * @author Cam Moore
 *
 */
public class JPAWattDepository extends WattDepository {

  /**
   * @param name The name of the Depository.
   * @param measurementType The type of measurement this Depository handles.
   */
  protected JPAWattDepository(String name, String measurementType) {
    this.name = name;
    this.measurementType = measurementType;
  }
  
  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#getMeasurements(org.wattdepot.server.datamodel.Sensor, java.util.Date, java.util.Date)
   */
  @Override
  public List<Measurement> getMeasurements(Sensor sensor, Date start, Date end) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#getValue(org.wattdepot.server.datamodel.Sensor, java.util.Date)
   */
  @Override
  public Double getValue(Sensor sensor, Date timestamp) throws NoMeasurementException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#getValue(org.wattdepot.server.datamodel.Sensor, java.util.Date, java.util.Date)
   */
  @Override
  public Double getValue(Sensor sensor, Date start, Date end) throws NoMeasurementException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#getValue(org.wattdepot.server.datamodel.Sensor, java.util.Date, java.util.Date, java.lang.Long)
   */
  @Override
  public Double getValue(Sensor sensor, Date start, Date end, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#getValue(org.wattdepot.server.datamodel.Sensor, java.util.Date, java.lang.Long)
   */
  @Override
  public Double getValue(Sensor sensor, Date timestamp, Long gapSeconds)
      throws NoMeasurementException, MeasurementGapException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.wattdepot.server.depository.WattDepository#putMeasurement(org.wattdepot.server.datamodel.Measurement)
   */
  @Override
  public void putMeasurement(Measurement meas) throws MeasurementTypeException {
    // TODO Auto-generated method stub
    
  }

}
