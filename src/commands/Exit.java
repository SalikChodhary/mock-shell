package commands;

/**
 * End the current shell session
 */

public class Exit extends Command {

  /**
   * Runs the exit command
   * 
   * @return String null
   */
  public String run() {
    System.exit(0);
    return null;
  }
}
