package mockobjects;

import error.ErrorChecker;
import filesystem.Directory;
import filesystem.File;
import interfaces.IFileSystem;

/**
 * Mock FileSystem, to be used for tesing purposes, provides the ability to set up a mock
 * environment on instantiation.
 */
public class MockFileSystem implements IFileSystem {
  /**
   * Root of fileSystem
   */
  private Directory root;
  /**
   * Holds the current working directory in the fileSystem
   */
  private Directory currentDir = root;
  // -------Used for initializing mock environment
  public Directory dir1;
  public Directory dir2;
  public Directory dir3;
  public Directory dir4;
  public File root_file1;
  public File file2;
  // ----------------------------------------------
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
   * Enum that holds the different types of Environments that can be initialized
   */
  public enum Environment {
    MOCK, EMPTY
  };

  /**
   * 
   * Gets the root directory of the file system
   * 
   * @param initType - type of environment to be initialized
   * @return Directory holding state of root directory
   */

  public MockFileSystem(Environment initType) {
    this.root = new Directory(ROOT_DIR);
    this.setCurrentDir(this.getRoot());

    if (initType == Environment.MOCK)
      initMock();
  }

  /**
   * Initializes mock environment, it looks like the following:
   * 
   * root: dir1: file2 dir2: dir4: dir3: root_file1
   */
  private void initMock() {
    dir1 = new Directory("dir1");
    dir2 = new Directory("dir2");
    dir3 = new Directory("dir3");
    dir4 = new Directory("dir4");
    root_file1 = new File("root_file1");
    file2 = new File("file2");

    dir1.addChild(file2);
    dir2.addChild(dir4);

    this.getRoot().addChild(dir1);
    this.getRoot().addChild(dir2);
    this.getRoot().addChild(root_file1);
    this.getRoot().addChild(dir3);

    file2.setContents("MOCK: file2 contents");
    root_file1.setContents("MOCK: root_file1 contents");
  }

  /**
   * Getter for root
   * 
   * @return Directory - the instance of the root directory
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
    currentDir = dir;
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

    if (curr.getName() == ROOT_DIR) {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((CURR_DIR == null) ? 0 : CURR_DIR.hashCode());
    result = prime * result + ((PARENT_DIR == null) ? 0 : PARENT_DIR.hashCode());
    result = prime * result + ((ROOT_DIR == null) ? 0 : ROOT_DIR.hashCode());
    result = prime * result + ((currentDir == null) ? 0 : currentDir.hashCode());
    result = prime * result + ((dir1 == null) ? 0 : dir1.hashCode());
    result = prime * result + ((dir2 == null) ? 0 : dir2.hashCode());
    result = prime * result + ((dir3 == null) ? 0 : dir3.hashCode());
    result = prime * result + ((dir4 == null) ? 0 : dir4.hashCode());
    result = prime * result + ((file2 == null) ? 0 : file2.hashCode());
    result = prime * result + ((root == null) ? 0 : root.hashCode());
    result = prime * result + ((root_file1 == null) ? 0 : root_file1.hashCode());
    return result;
  }

  @SuppressWarnings("unused")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MockFileSystem other = (MockFileSystem) obj;
    if (CURR_DIR == null) {
      if (other.CURR_DIR != null)
        return false;
    } else if (!CURR_DIR.equals(other.CURR_DIR))
      return false;
    if (PARENT_DIR == null) {
      if (other.PARENT_DIR != null)
        return false;
    } else if (!PARENT_DIR.equals(other.PARENT_DIR))
      return false;
    if (ROOT_DIR == null) {
      if (other.ROOT_DIR != null)
        return false;
    } else if (!ROOT_DIR.equals(other.ROOT_DIR))
      return false;
    if (currentDir == null) {
      if (other.currentDir != null)
        return false;
    } else if (!currentDir.equals(other.currentDir))
      return false;
    if (dir1 == null) {
      if (other.dir1 != null)
        return false;
    } else if (!dir1.equals(other.dir1))
      return false;
    if (dir2 == null) {
      if (other.dir2 != null)
        return false;
    } else if (!dir2.equals(other.dir2))
      return false;
    if (dir3 == null) {
      if (other.dir3 != null)
        return false;
    } else if (!dir3.equals(other.dir3))
      return false;
    if (dir4 == null) {
      if (other.dir4 != null)
        return false;
    } else if (!dir4.equals(other.dir4))
      return false;
    if (file2 == null) {
      if (other.file2 != null)
        return false;
    } else if (!file2.equals(other.file2))
      return false;
    if (root == null) {
      if (other.root != null)
        return false;
    } else if (!root.equals(other.root))
      return false;
    if (root_file1 == null) {
      if (other.root_file1 != null)
        return false;
    } else if (!root_file1.equals(other.root_file1))
      return false;
    return true;
  }
}
