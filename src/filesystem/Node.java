package filesystem;

import java.io.Serializable;

/**
 * Parent class of the File and Directory class
 */
public class Node implements Serializable {
  /**
   * Needed for serialization
   */
  private static final long serialVersionUID = 1661371888708561021L;
  /**
   * Stores the name of the Node
   */
  private String name;
  /**
   * Stores the parent directory of the node
   */
  private Directory parent;

  /**
   * Gets the parent of the node
   * 
   * @return Directory Parent directory of node
   */
  public Directory getParent() {
    return parent;
  }

  /**
   * Sets the parent of the node
   * 
   * @param parent New parent of node
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Gets the name of the node
   * 
   * @return String name of the node
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the node
   * 
   * @param name new name of the node
   */
  public void setName(String name) {
    this.name = name;
  }
}
