package filesystem;

/**
 * Allows a user to create files, set content and also inherits from the Node class.
 */
public class File extends Node {
  /**
   * Required for serialization 
   */
  private static final long serialVersionUID = -3815874957464904274L;
  /**
   * String representing the contents of a file
   */
  private String contents;

  /**
   * Constructor for file object
   * 
   * @param name of the file
   */
  public File(String name) {
    this.setName(name);
  }

  /**
   * Getter for contents of file
   * 
   * @return String Current state of the contents of the string
   */
  public String getContents() {
    return contents;
  }

  /**
   * Setter for the contents of file
   * 
   * @param contents new contents of file
   */
  public void setContents(String contents) {
    this.contents = contents;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contents == null) ? 0 : contents.hashCode());
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
    File other = (File) obj;
    if (contents == null) {
      if (other.contents != null)
        return false;
    } else if (!contents.equals(other.contents))
      return false;
    return true;
  }
}
