package helpers;

import error.ErrorChecker;
import error.StandardError;
import filesystem.Directory;
import filesystem.File;
import interfaces.IFileSystem;
import interfaces.IRedirection.RedirectionType;

/**
 * Prints strings to standard output
 */

public class Output {

  /**
   * Prints s to standard output
   * 
   * @param s the string to be printed
   */
  public static void print(String s) {
    System.out.println(s);
  }

  /**
   * Prints an empty string
   */
  public static void nullCommandPrint() {
    System.out.print("");
  }

  /**
   * Redirection helper
   * 
   * @param fileSystem - Current filesystem
   * @param s - string to be redirected
   * @param name - name of file to be redirected to
   * @param type - type of redirection - append/overwrite
   */
  private static void redirectHelper(IFileSystem fileSystem, String s, String name,
      RedirectionType type) {
    File file;

    if (!ErrorChecker.checkValidDirFileName(name))
      return;

    if (fileSystem.getCurrentDir().hasChild(name)) {
      if (fileSystem.getCurrentDir().getDirChildByName(name) != null) {
        StandardError.allErrors.add("err: " + name + " is a directory");
        return;
      }
      file = fileSystem.getCurrentDir().getFileChildByName(name);
      file.setContents(type == RedirectionType.APPEND ? file.getContents().concat("\n" + s) : s);

    } else {
      file = new File(name);
      file.setContents(s);
      fileSystem.getCurrentDir().addChild(file);
    }
  }

  /**
   * Provides functionality to redirect output to files rather than to shell
   * 
   * @param s string that needs to be redirected to a file
   * @param path represents the path to the file that needs to be redirected to
   * @param type redirection type, defined as OVERWRITE or APPEND
   */

  public static void redirect(IFileSystem fileSystem, String s, String path, RedirectionType type) {
    Directory startingDir = fileSystem.getCurrentDir();

    if (ErrorChecker.hasConsecutiveSlash(path))
      return;

    if (path.compareTo("/") == 0) {
      StandardError.allErrors.add("Err: redirection file does not exist");
      return;
    }

    String splitPath[] = path.split("/");
    String name = splitPath[splitPath.length - 1];

    String parent =
        splitPath.length > 1 ? path.substring(0, path.length() - name.length() - 1) : "";

    if (splitPath.length == 2 && splitPath[0].isEmpty() && path.startsWith("/"))
      parent = "/";

    if (!fileSystem.goToPath(parent)) { // check if parent is valid in current filesystem
      StandardError.allErrors.add("Err: redirection file does not exist");
      return;
    }

    redirectHelper(fileSystem, s, name, type);
    fileSystem.setCurrentDir(startingDir);
  }


}
