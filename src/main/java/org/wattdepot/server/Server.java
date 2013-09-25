/**
 * 
 */
package org.wattdepot.server;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author Cam Moore
 * 
 */
public class Server {
  private EntityManager em;

  private static Server instance;
  
  /**
   * Creates the Server instance.
   */
  private Server() {
    em = Persistence
        .createEntityManagerFactory("org.wattdepot.datamodel.jpa").createEntityManager();
  }

  /**
   * @return The singleton instance.
   */
  public static Server getInstance() {
    if (instance == null) {
      instance = new Server();
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
