package error;

import java.util.ArrayList;
import helpers.Output;

/**
 * Standard error class that handles printing to user as well as storing all errors
 */
public class StandardError {
  /**
   * allErrors contains all the errors as strings
   */
  public static ArrayList<String> allErrors = new ArrayList<String>();

  /**
   * Prints all errors to standard output and clears the allErrors
   */
  public static void printAllErrors() {
    for (String error : allErrors) {
      Output.print(error);
    }
    allErrors.clear();
  }
}
