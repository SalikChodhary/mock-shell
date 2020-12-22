package interfaces;

/**
 * Interface for directory stack
 */
public interface IDirStack {

  /**
   * Pushes given path onto stack
   * 
   * @param dirPath path to a directory
   */
  public void pushOnStack(String dirPath);

  /**
   * Pops path ontop of stack and returns
   * 
   * @return String of poped path
   */
  public String popStack();

}
