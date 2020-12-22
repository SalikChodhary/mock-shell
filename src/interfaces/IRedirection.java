package interfaces;

import java.util.HashSet;

/**
 * Interface for redirection, any class that implements shoiuld provide the ability to redirect
 * output
 */
public interface IRedirection {
  /**
   * Set that contains set of all commands that support redirection, can be used for error checking
   * for invalid redirection
   */
  @SuppressWarnings("serial")
  public HashSet<String> redirectionSupportedCommands = new HashSet<String>() {
    {
      add("echo");
      add("man");
      add("ls");
      add("cat");
      add("history");
      add("pwd");
      add("tree");
      add("search");
    }
  };

  /**
   * This defines the various types of redirections this fileSystem allows
   */
  enum RedirectionType {
    APPEND, OVERWRITE, NONE
  }
}

