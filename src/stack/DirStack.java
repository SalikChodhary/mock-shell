package stack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Stack;
import interfaces.IDirStack;

/*
 * This class keeps track of a stack that holds full path names
 *
 */
public class DirStack implements Serializable, IDirStack {
  /**
   * Required for serialization
   */
  private static final long serialVersionUID = 3317455530083740968L;
  private static DirStack stackInstance;
  public Stack<String> dirStack;

  private DirStack() {
    this.dirStack = new Stack<String>();
  }

  /**
   * @param dirPath
   */
  public void pushOnStack(String dirPath) {
    dirStack.push(dirPath);
  }

  /**
   * @return String
   */
  public String popStack() {
    return dirStack.pop();
  }

  /**
   * @return DirStack
   */
  public static DirStack getInstance() {
    return stackInstance == null ? stackInstance = new DirStack() : stackInstance;
  }

  /**
   * Overrides the readObject method from Serializable, needed to ensure the integrity of the
   * singleton pattern
   */
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    ois.defaultReadObject();
    stackInstance = this; // rather than creating new instance, assign current object to INSTANCE
  }

  /**
   * Overrides the readResolve method from Serializable, needed to ensure the integrity of the
   * singleton pattern
   */
  private Object readResolve() {
    return stackInstance; // return INSTANCE.
  }
}
