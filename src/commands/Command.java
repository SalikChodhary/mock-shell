
package commands;

import java.util.ArrayList;
import error.StandardError;
import helpers.Output;
import interfaces.IFileSystem;

/**
 * Superclass for all commands, and provides the ability to redirect output
 */
public class Command {
  /**
   * Stores user-provided arguments
   */
  private ArrayList<String> args;
  /**
   * Stores the current command being executed
   */
  private String currentCommand;

  /**
   * Stores the filesystem instance, allows dependencey injection
   */
  private IFileSystem fs;
 
  /**
   * Stores raw user input
   */
  private String rawUserInput;

  /**
   * Getter for rawUserinput
   * @return String - rawUserInput
   */
  public String getRawUserInput() {
    return rawUserInput;
  }

  /**
   * Setter for rawUserInput
   * 
   * @param rawUserInput
   */
  public void setRawUserInput(String rawUserInput) {
    this.rawUserInput = rawUserInput;
  }

  /**
   * Get the instance of file system
   * 
   * @return IFileSystem - the instance of the file system
   */
  public IFileSystem getFileSystem() {
    return fs;
  }

  /**
   * Provides functionality to update the fs field
   * 
   * @param fileSystem - the new file system to be updated to
   */
  public void setFileSystem(IFileSystem fileSystem) {
    this.fs = fileSystem;
  }

  /**
   * Getter for currentCommand
   * 
   * @return String current value of currentCommand field member
   */
  public String getCurrentCommand() {
    return currentCommand;
  }

  /**
   * Setter for currentCommand
   * 
   * @param currentCommand new value for currentCommand field
   */
  public void setCurrentCommand(String currentCommand) {
    this.currentCommand = currentCommand;
  }

  /**
   * Getter for args arraylist
   * 
   * @return ArrayList<String> Reference to current args from user
   */
  public ArrayList<String> getArgs() {
    return args;
  }

  /**
   * Setter for args arraylist
   * 
   * @param args contains new args for command
   */
  public void setArgs(ArrayList<String> args) {
    this.args = args;
  }

  /**
   * Default run, simply prints new line. This is called when the user gives no input and presses
   * enter
   */
  public String run() {
    Output.nullCommandPrint();
    return null;
  }

  /**
   * Provides commands the ability to output to the user through the terminal
   * 
   * @param s represents the string to be outputted to the user
   */
  public void output(String s) {
    Output.print(s);
  }


  /**
   * Add new error to Standard Error
   * 
   * @param err represents new error to be Standard Error
   */
  public void addNewError(String err) {
    StandardError.allErrors.add(err);
  }

}
