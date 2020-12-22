package commands;

import interfaces.IFileSystem;

/**
 * Gets the current working directory
 */

public class PrintWorkingDirectory extends Command {

  /**
   * Runs the pwd command
   * 
   * @return String the current working directory
   */
  public String run() {
    IFileSystem fileSystem = super.getFileSystem();

    return fileSystem.getPath();
  }
}
