package filesystem;

import java.util.ArrayList;

/**
 * This class defines the Directory object and methods related to handling directories
 */
public class Directory extends Node {

  /**
   * Required for serialization
   */
  private static final long serialVersionUID = 1531173734381443084L;
  /**
   * ArrayList of all children nodes. Children can be files or directories.
   */
  private ArrayList<Node> children;

  /**
   * Constructor to create new Directory object given the name of the directory
   * 
   * @param name string representing the name of the directory to be created
   */
  public Directory(String name) {
    super.setName(name);
    this.children = new ArrayList<Node>();
  }

  /**
   * Constructor to create new Directory object given the name and parent of the directory
   * 
   * @param name String representing the name of the directory to be created
   * @param parent Parent directory of the directoru to eb created
   */
  public Directory(String name, Directory parent) {
    super.setName(name);
    this.children = new ArrayList<Node>();
    super.setParent(parent);
  }

  /**
   * Getter for the list of children nodes
   * 
   * @return ArrayList<Node> reference to list of children nodes
   */
  public ArrayList<Node> getChildren() {
    return children;
  }

  /**
   * Checks if directory contains child node, given child node object
   * 
   * @param child Node representing the child node to check for
   * @return boolean if there exists a child node that is equal to child
   */
  public boolean hasChild(Node child) {
    for (Node currChild : this.getChildren()) {
      if (currChild.getName().compareTo(child.getName()) == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if directory contains child node, given child node name
   * 
   * @param childName String representing the name of the child node that is being searched for
   * @return boolean if there exists a child node with the name childName
   */
  public boolean hasChild(String childName) {
    for (Node currChild : this.getChildren()) {
      if (currChild.getName().compareTo(childName) == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Provides functionality to add a new node as a child
   * 
   * @param child new node to be added as a child
   * @return boolean operation success
   */
  public boolean addChild(Node child) {
    if (this.hasChild(child))
      return false;

    this.children.add(child);
    child.setParent(this);

    return true;
  }

  /**
   * Provides funcitonality to search for File node that corresponds to name
   * 
   * @param name name of the file child that is being searched for
   * @return File that corresponds to name
   */
  public File getFileChildByName(String name) {
    for (Node child : this.children) {
      if (child instanceof File && child.getName().equals(name))
        return (File) child;
    }
    return null;
  }

  /**
   * Provides funcitonality to search for Directory node that corresponds to name
   * 
   * @param name of the Directory child that is being searched for
   * @return Directory that corresponds to name
   */
  public Directory getDirChildByName(String name) {
    for (Node child : this.children) {
      if (child instanceof Directory && child.getName().equals(name))
        return (Directory) child;
    }
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((children == null) ? 0 : children.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Directory other = (Directory) obj;
    if (other.getName() != this.getName())
      return false;
    if (children == null) {
      if (other.children != null)
        return false;
    } else if (!children.equals(other.children))
      return false;
    return true;
  }
}
