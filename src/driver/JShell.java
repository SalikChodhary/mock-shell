package driver;

import parser.Parser;
import java.util.Scanner;
import error.StandardError;

/**
 * Shell that interacts with a Mock File System by the use of various commands
 */
public class JShell {
  /**
   * Continuously asks user for input and calls on Parser class to parse user input
   * 
   * @param args command line arguments sent by user
   */
  public static void main(String[] args) {
    Parser parser = new Parser();

    @SuppressWarnings("resource")
    Scanner scan = new Scanner(System.in);

    while (true) {
      System.out.print("/#:");
      String input = scan.nextLine();
      parser.parse(input);
      StandardError.printAllErrors();
    }
  }
}
