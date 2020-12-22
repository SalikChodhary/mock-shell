package commands;

import java.util.ArrayList;
import filesystem.*;
import helpers.Pair;
import interfaces.IFileSystem;


/**
 * This class outputs the contents of a specified directory or returns the name of specified file
 * with an option of recursivly finding
 * 
 * @return output - the found directories and/or files
 */

public class ListDirectory extends Command {
  public String run() {
    // get instance of filesystem arguments, and currentdierctory
    // decalre output and boolean to check for -R option
    IFileSystem fileSystem = super.getFileSystem();
    ArrayList<String> args = super.getArgs();
    Directory curDir = fileSystem.getCurrentDir();
    boolean isR = false;
    String output = "";

    if (args.size() != 0 && args.get(0).equals("-R")) {
      args.remove(0);
      isR = true;
    }

    if (args.size() == 0) {
      if (isR) {
        output = lsROutputDir(curDir, 0, output);
      } else {
        output = lsOutputDir(curDir, output);
      }
      output += "\n";
    }

    for (String arg : args) {
      boolean error = false;
      if (fileSystem.goToPath(arg) == false) {
        // Tuple: String - output, Boolean - checks wether an error has occured
        Pair<String, Boolean> pair = caseFile(arg, curDir, fileSystem, output);
        output = pair.first;
        error = pair.second;

      } else {
        output = caseDir(output, isR, fileSystem);
        fileSystem.setCurrentDir(curDir);
      }

      if (error)
        break;
    }
    if (output.length() != 0) {
      output = output.substring(0, output.length() - 1);
      return output;
    }
    return null;

  }

  /**
   * Outputs recursivly the children node names of a directory node
   * 
   * @param dir - A directory node
   * @param depth - the depth of recursion
   * @param output - output string
   * @return void
   */

  private String lsROutputDir(Directory dir, int depth, String output) {

    ArrayList<Node> children = dir.getChildren();
    // super.output(" ".repeat(Math.max(depth-1, 0)) + dir.getName() + ":");
    output += ("     ".repeat(Math.max(depth - 1, 0)) + dir.getName() + ":" + "\n");
    for (Node child : children) {
      if (child.getClass() == File.class) {
        // super.output(" ".repeat(depth)+ child.getName());
        output += ("     ".repeat(depth) + child.getName() + "\n");
      } else {
        output = lsROutputDir((Directory) child, depth + 1, output);
      }
    }
    return output;


  }

  /**
   * Outputs children node names of a directory node
   * 
   * @param dir - A directory node
   * @param output - output string
   * @return void
   */

  private String lsOutputDir(Directory dir, String output) {

    ArrayList<Node> children = dir.getChildren();
    // super.output(dir.getName() + ":");
    output += (dir.getName() + ":" + "\n");
    for (Node child : children) {
      output += (child.getName() + "\n");
    }

    return output;
  }

  /**
   * Considers the case of ls command for files
   * 
   * @param arg - one of the arugments for ls
   * @param curDir - the current directory node
   * @param fileSystem - instance of filesystem
   * @param output - output String
   * @return caseFile - Tuple of (output string, error to indicate error)
   */

  private Pair<String, Boolean> caseFile(String arg, Directory curDir, IFileSystem fileSystem,
      String output) {

    // the split of names for from the argument
    String[] splitArg = arg.split("/");
    if (splitArg.length == 1) {

      if (curDir.getFileChildByName(splitArg[0]) != null) {
        // super.output(splitArg[0]);
        output += (splitArg[0] + "\n");

      } else {
        // super.output("Err: ls: no such file or directory " + arg + "\n");
        super.addNewError("Err: ls: no such file or directory " + arg + "\n" + "\n");
        Pair<String, Boolean> pair = new Pair<String, Boolean>(output, true);
        return pair;
      }
    } else {

      int filelength = splitArg[splitArg.length - 1].length();

      // the path to parentdirectory
      String argParentDir = arg.substring(0, arg.length() - (filelength + 1));
      if (fileSystem.goToPath(argParentDir) == false) {
        super.addNewError("Err: ls: no such file or directory " + arg);
        Pair<String, Boolean> pair = new Pair<String, Boolean>(output, true);
        return pair;
      } else {

        // parent directory
        Directory parentDir = fileSystem.getCurrentDir();
        if (parentDir.getFileChildByName(splitArg[splitArg.length - 1]) != null) {
          // super.output(arg + "\n");
          output += (arg + "\n" + "\n");
          fileSystem.setCurrentDir(curDir);
          Pair<String, Boolean> pair = new Pair<String, Boolean>(output, false);
          return pair;
        } else {
          // super.output("Err: ls: no such file or directory" + arg + "\n");
          super.addNewError("Err: ls: no such file or directory " + arg);
          fileSystem.setCurrentDir(curDir);
          Pair<String, Boolean> pair = new Pair<String, Boolean>(output, true);
          return pair;
        }

      }
    }
    Pair<String, Boolean> pair = new Pair<String, Boolean>(output, false);
    return pair;

  }

  /**
   * Considers the case of ls command for dir
   * 
   * @param output - the output string
   * @param isR - indicates if Recursive or not
   * @param fileSystem - instance of filesystem
   * @return output - the output string
   */

  private String caseDir(String output, boolean isR, IFileSystem fileSystem) {
    // get parent directory
    Directory parentDir = fileSystem.getCurrentDir();
    if (isR) {
      output = lsROutputDir(parentDir, 0, output);
    } else {
      output = lsOutputDir(parentDir, output);
    }
    output += "\n";
    return output;
  }

}
