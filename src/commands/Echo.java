package commands;

import error.ErrorChecker;

/**
 * Repeats the string to standard output or file where the string can overwrite or append to the
 * contents of the file
 */

public class Echo extends Command {

  /**
   * Runs the echo command, and returns the requested string
   * 
   * @return String - the string inputted is returned back
   */
  public String run() {
    String s = super.getRawUserInput();
    if (s.matches("\\s*(echo)\\s*")) {
      super.addNewError("Err: echo: check arguments");
      return null;
    }
    s = s.trim().substring(5).trim();
    if (ErrorChecker.checkValidString(s)) {
      return s.substring(1, s.length() - 1);
    } else
      return null;
  }
}
