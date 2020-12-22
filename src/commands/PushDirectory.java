package commands;

import java.util.ArrayList;
import interfaces.IFileSystem;
import interfaces.IDirStack;

/*
 * This class adds current directory to a stack then changes working directory to specified path
 *
 */

public class PushDirectory extends Command {
  /**
   * Stack containing directories 
   */
  private IDirStack stack;

  /**
   * Constructor for PushDirectory
   * 
   * @param stack - the instance of stack
   */

  public PushDirectory(IDirStack stack) {
    this.stack = stack;
  }

  /**
   * Runs the command PushDirectory
   * 
   * @return output - output string
   */

  public String run() {
    // Creates a list to hold arguments for the command
    ArrayList<String> args = super.getArgs();


    // Create instance of Filesystem
    IFileSystem fileSystem = super.getFileSystem();
    if (args.size() > 1) {
      super.addNewError("Err: too many args");
      return null;
    }

    // Path of the that filesytem sets to
    String Path = args.get(0);

    // Get full path name of the current directory
    String curPath = fileSystem.getPath();

    if (fileSystem.goToPath(Path) == false) {
      super.addNewError("Err: pushd: Invalid Path");
      // super.output("Err: pushd: no such directory");
    } else {

      // Get instance of stack to push current directory to
      stack.pushOnStack(curPath);
    }

    return null;


  }
}
