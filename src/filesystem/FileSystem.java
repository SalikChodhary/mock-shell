package filesystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import error.ErrorChecker;
import interfaces.IFileSystem;

/**
 * FileSystem class holds the current state of the file tree, that includes directories and files
 */
public class FileSystem implements IFileSystem, Serializable {

  /**
   * Needed for serialization
   */
  private static final long serialVersionUID = -8879531712209813022L;

  /**
   * Stores the current single instance of the file system
   */
  private static FileSystem fileSystemInstance;

  /**
   * Constant value of parent dir
   */
  private final String PARENT_DIR = "..";

  /**
   * Constant value of root dir
   */
  private final String ROOT_DIR = "/";

  /**
   * Constant value of current directory
   */
  private final String CURR_DIR = ".";

  /**
   * Root is initalized once, and is never changed
   */
  private final Directory root;

  /**
   * Holds the current directory the filesystem is in at any given moment
   */
  private Directory currentDir;

  /**
   * Gets the root directory of the file system
   * 
   * @return Directory holding state of root directory
   */
  public Directory getRoot() {
    return root;
  }

  /**
   * Gets the current working directory in the file system
   * 
   * @return Directory
   */
  public Directory getCurrentDir() {
    return currentDir;
  }

  /**
   * Sets the current working directory in the file system
   * 
   * @param dir new directory that needs to be set as the working directory
   */
  public void setCurrentDir(Directory dir) {
    this.currentDir = dir;
  }

  /**
   * Constructs a new FileSystem object
   */
  private FileSystem() {
    this.root = new Directory(ROOT_DIR);
    this.setCurrentDir(this.root);
  }

  /**
   * Gets the existing instance of the filesystem or creates a new one if it does not exist
   * 
   * @return FileSystem
   */
  public static FileSystem getInstance() {
    return fileSystemInstance == null ? fileSystemInstance = new FileSystem() : fileSystemInstance;
  }

  /**
   * Updates currentDir to a child of the current currentDir
   * 
   * @param reqChild string representing a name of a directory
   * @return boolean operation success
   */
  public boolean goToChildDirOfCurrentDir(String reqChild) {
    Directory child = this.getCurrentDir().getDirChildByName(reqChild);

    if (child != null) {
      this.setCurrentDir(child);
      return true;
    }

    return false;
  }

  /**
   * Set currentDir to the parent of the current currentDir
   * 
   * @return boolean operation success
   */
  public boolean goToParentOfCurrentDir() {
    if (this.getCurrentDir().equals(this.getRoot()))
      return false;

    this.setCurrentDir(this.getCurrentDir().getParent());

    return true;
  }

  /**
   * Traverses to the given path, and sets the currentDir to the resulting directory
   * 
   * @param path String representing a path to traverse to
   * @return boolean operation success
   */
  public boolean goToPath(String path) {
    Directory fallback = this.getCurrentDir();

    if (path.isEmpty())
      return true;

    if (ErrorChecker.hasConsecutiveSlash(path))
      return false;

    if (path.compareTo(ROOT_DIR) == 0) {
      this.setCurrentDir(this.getRoot());
      return true;
    }

    if (path.startsWith("/"))
      this.setCurrentDir(this.getRoot());

    String[] toVisit = path.split("/");

    for (String directory : toVisit) {
      if (directory.isEmpty() || directory.compareTo(CURR_DIR) == 0)
        continue;
      if (directory.compareTo(PARENT_DIR) == 0) {
        if (!this.goToParentOfCurrentDir()) {
          this.setCurrentDir(fallback);
          return false;
        }
        continue;
      }
      if (!this.goToChildDirOfCurrentDir(directory)) {
        this.setCurrentDir(fallback);
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the full path of the current working directory
   * 
   * @return String the full path of the current working directory
   */
  public String getPath() {
    Directory curr = currentDir;

    if (curr.getName().equals(ROOT_DIR)) {
      return ROOT_DIR;
    }

    StringBuilder path = new StringBuilder();

    while (curr != getRoot()) {
      path.insert(0, curr.getName() + "/");
      curr = curr.getParent();
    }

    path.insert(0, "/");
    return path.substring(0, path.length() - 1);
  }

  /**
   * Removes the current directory from the filesystem
   * 
   * @return boolean true upon successfull deletion and false otherwise
   */
  public boolean removeCurrentDirectory() {
    Directory current = this.getCurrentDir();

    if (!goToParentOfCurrentDir()) {
      return false;
    }

    if (!this.getCurrentDir().getChildren().remove(current)) {
      return false;
    }
    return true;
  }

  /**
   * Overrides the readObject method from Serializable, needed to ensure the integrity of the
   * singleton pattern
   */
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    ois.defaultReadObject();
    fileSystemInstance = this;// rather than creating new instance, assign current object to
                              // instance
  }

  /**
   * Overrides the readResolve method from Serializable, needed to ensure the integrity of the
   * singleton pattern
   */
  private Object readResolve() {
    return fileSystemInstance;
  }
}
