package commands;


import interfaces.IFileSystem;

/**
 * Allows traversing to an exiting directory in the filesystem
 */
public class ChangeDirectory extends Command {

  /**
   * This function provides the ability to execute the "cd" command
   * 
   * @return String - any user output
   */
  public String run() {
    IFileSystem fileSystem = super.getFileSystem();

    boolean res = fileSystem.goToPath(super.getArgs().get(0));

    if (!res)
      super.addNewError("Err: cd: path does not exist");

    return null;
  }
}
