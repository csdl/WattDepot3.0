/**
 * Slug.java created on Nov 17, 2013 by Cam Moore.
 */
package org.wattdepot3.util;

/**
 * Slug - Utility class to slugify strings.
 * 
 * @author Cam Moore
 * 
 */
public class Slug {

  /**
   * Converts to lowercase, removes non-word characters (alphanumerics and
   * underscores) and converts spaces to hyphens. Also strips leading and
   * trailing whitespace.
   * 
   * @param str
   *          The string to slugify.
   * @return The slugified String.
   */
  public static String slugify(String str) {
    String ret = null;
    ret = str.toLowerCase();
    ret = ret.replace(" ", "_");
    ret = ret.replace("!", "");
    ret = ret.replace("@", "");
    ret = ret.replace("#", "");
    ret = ret.replace("$", "");
    ret = ret.replace("%", "");
    ret = ret.replace("^", "");
    ret = ret.replace("&", "");
    ret = ret.replace("*", "");
    ret = ret.replace("(", "");
    ret = ret.replace(")", "");
    ret = ret.replace("=", "");
    ret = ret.replace("+", "");
    ret = ret.replace("`", "");
    ret = ret.replace("~", "");
    ret = ret.replace(",", "");
    ret = ret.replace("<", "");
    ret = ret.replace(".", "");
    ret = ret.replace(">", "");
    ret = ret.replace("/", "");
    ret = ret.replace("?", "");
    ret = ret.replace(";", "");
    ret = ret.replace(":", "");
    ret = ret.replace("[", "");
    ret = ret.replace("{", "");
    ret = ret.replace("]", "");
    ret = ret.replace("}", "");
    ret = ret.replace("\\", "");
    ret = ret.replace("|", "");
    return ret;
  }
}
