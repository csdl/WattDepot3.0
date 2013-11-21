/**
 * UserPassword.java created on Nov 8, 2013 by Cam Moore.
 */
package org.wattdepot3.datamodel;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * UserPassword - UserId and encrypted password pair.
 * 
 * @author Cam Moore
 * 
 */
public class UserPassword {
  /** The password for the admin user. */
  public static final UserPassword ADMIN = new UserPassword(UserInfo.ADMIN.getId(), "admin");
  private String id;
  private String encryptedPassword;
  private String plainText;
  private StrongPasswordEncryptor passwordEncryptor;

  static {
    String password = System.getenv("wattdepot-server.admin.password");
    if (password != null) {
      ADMIN.setPassword(password);
    }
  }

  /**
   * Default constructor.
   */
  public UserPassword() {
    this.passwordEncryptor = new StrongPasswordEncryptor();
  }

  /**
   * Creates a new UserPassword object, encrypting the plainTextPassword and
   * storing the hash.
   * 
   * @param id
   *          The user's id.
   * @param plainTextPassword
   *          The plain text password.
   */
  public UserPassword(String id, String plainTextPassword) {
    this.passwordEncryptor = new StrongPasswordEncryptor();
    this.id = id;
    this.plainText = plainTextPassword;
    this.encryptedPassword = passwordEncryptor.encryptPassword(plainTextPassword);
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
    result = prime * result + ((encryptedPassword == null) ? 0 : encryptedPassword.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserPassword other = (UserPassword) obj;
    if (encryptedPassword == null) {
      if (other.encryptedPassword != null) {
        return false;
      }
    }
    else if (!encryptedPassword.equals(other.encryptedPassword)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  /**
   * @return the plainText
   */
  public String getPlainText() {
    return plainText;
  }

  /**
   * @param plainText the plainText to set
   */
  public void setPlainText(String plainText) {
    this.plainText = plainText;
  }

  /**
   * Checks the given password.
   * 
   * @param inputPassword
   *          The password to check.
   * @return True if the password is correct.
   */
  public boolean checkPassword(String inputPassword) {
    return passwordEncryptor.checkPassword(inputPassword, encryptedPassword);
  }

  /**
   * @return the encryptedPassword
   */
  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param encryptedPassword
   *          the encryptedPassword to set
   */
  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets the encrypted password by encrypting the plain text.
   * 
   * @param plainText
   *          the plain text password.
   */
  public void setPassword(String plainText) {
    this.encryptedPassword = passwordEncryptor.encryptPassword(plainText);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserPassword [id=" + id + ", encryptedPassword=" + encryptedPassword + "]";
  }
  
}
