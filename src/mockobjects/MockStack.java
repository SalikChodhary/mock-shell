package mockobjects;

import interfaces.IDirStack;
import java.util.Stack;

/**
 * Mock Dir Stack to be used for testing purposes
 */
public class MockStack implements IDirStack {
  /**
   * Stack of directories
   */
  private Stack<String> dirStack = new Stack<String>();
  String path1 = "/dir2/dir4";
  String path2 = "/";
  String path3 = "/dir2";

  /**
   * Defines the types of environments
   */
  public enum Environment {
    MOCK, EMPTY
  };

  /**
   * Constructor for MockStack
   * @param initType - type of environment, either MOCK or EMPTY
   */
  public MockStack(Environment initType) {
    dirStack.push(path1);
    dirStack.push(path2);
    dirStack.push(path3);
  }

  /**
   * Provides ability to push a new path to the string
   * 
   * @param path - new path to be added
   */
  public void pushOnStack(String path) {
    dirStack.push(path);
  }

  /**
   * Provides ability to pop from directory stack
   * 
   * @return String - top path in directory stack
   */
  public String popStack() {
    return dirStack.pop();

  }
}
