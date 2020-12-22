package commands;

import interfaces.IFileSystem;

/**
 * Removes directory given from the filesystem
 */
public class Remove extends Command {

  /**
   * Runs the rm command
   * 
   * @return String null
   */
  public String run() {
    if (super.getArgs().get(0).equals("/")) {
      super.addNewError("Err: rm: cannot remove '/'");
      return null;
    }

    IFileSystem currFileSystem = super.getFileSystem();
    String currentPath = currFileSystem.getPath();

    if (!currFileSystem.goToPath(super.getArgs().get(0))) {
      super.addNewError("Err: rm: argument must be an existing directory");
      return null;
    }
    currFileSystem = super.getFileSystem();
    String newPath = currFileSystem.getPath();

    if (currentPath.contains(newPath)) {
      super.addNewError("Err: rm: cannot delete a parent directory or current directory");
      return null;
    }

    if (!currFileSystem.removeCurrentDirectory()) {
      super.addNewError("Err: rm: failed to delete " + super.getArgs().get(0));
      return null;
    }

    currFileSystem.goToPath(currentPath);
    return null;
  }
}
