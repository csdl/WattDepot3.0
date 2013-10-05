/**
 * 
 */
package org.wattdepot.server.depository.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author Cam Moore
 * 
 */
public class JPAManager {
  private EntityManager em;

  private static JPAManager instance;
  
  /**
   * Creates the Server instance.
   */
  private JPAManager() {
    em = Persistence
        .createEntityManagerFactory("org.wattdepot.datamodel.jpa").createEntityManager();
  }

  /**
   * @return The singleton instance.
   */
  public static JPAManager getInstance() {
    if (instance == null) {
      instance = new JPAManager();
    }
    return instance;
  }
  
  /**
   * @return The EntityManager.
   */
  public EntityManager getEntityManager() {
    return em;
  }
  
  @Override
  protected void finalize() {
    if (em != null) {
      em.close();
    }
  }
}
