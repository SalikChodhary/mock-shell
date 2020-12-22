package commands;

import java.util.ArrayList;
import filesystem.*;
import interfaces.IFileSystem;

/**
 * Provides the ability to perform the "tree" command
 */
public class Tree extends Command {

  /**
   * Runs the tree command
   * @return String - tree visualization of filesystem
   */
  public String run() {

    String output = "";
    IFileSystem fileSystem = super.getFileSystem();
    Directory root = fileSystem.getRoot();
    output = getOutput(root, 1, output);
    return output;

  }

  /**
   * Gets the string representation of the tree
   * 
   * @param dir - current dir
   * @param depth - number of recursive calls
   * @param output - string to be returned
   * @return String - tree visualization of filesystem
   */
  public String getOutput(Directory dir, int depth, String output) {
    ArrayList<Node> children = dir.getChildren();
    output += ("     ".repeat(Math.max(depth - 1, 0)) + dir.getName() + "\n");

    for (Node child : children) {
      if (child.getClass() == File.class) {
        output += ("     ".repeat(depth) + child.getName() + "\n");
      } else {
        output = getOutput((Directory) child, depth + 1, output);
      }
    }
    return output;
  }

}
