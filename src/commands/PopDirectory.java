package commands;

import interfaces.IFileSystem;
import interfaces.IDirStack;

/*
 * This class pops a directory path from stack class and moves current directory to poped directory
 * path
 *
 */

public class PopDirectory extends Command {
  /**
   * Stack containing directories 
   */
  private IDirStack stack;

  /**
   * Constructor for PopDirectory
   * @param stack - stack instance
   */
  public PopDirectory(IDirStack stack) {
    this.stack = stack;
  }

  /**
   * Runs the command PopDirectory
   * 
   * @return String - null
   */

  public String run() {

    // set path to eventually take value of path poped form stack
    String toCD;

    try {
      toCD = stack.popStack();

      // get instance of filesystem
      IFileSystem fileSystem = super.getFileSystem();
      fileSystem.goToPath(toCD);

    } catch (Exception EmptyStackException) {
      // super.output("Err: popd: empty stack");
      super.addNewError("Err: popd: empty stack");
    }
    return null;
  }


}
