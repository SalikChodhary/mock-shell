package commands;

import java.util.ArrayList;
import filesystem.*;
import interfaces.IFileSystem;

/**
 * This class used for searching files and directories
 */

public class Search extends Command {

  /**
   * runs the cmd for searching files and directories
   * 
   * @return output - the found directories and/or files
   */

  public String run() {
    IFileSystem fileSystem = super.getFileSystem();
    ArrayList<String> args = super.getArgs();
    Directory curDir = fileSystem.getCurrentDir();
    String output = "";
    String searchName = args.get(args.size() - 1);
    Node mockNode = checkParamsAndMockNode(args);

    if (mockNode == null) {
      return output;
    }

    ArrayList<String> searchArgs = new ArrayList<String>();

    for (int i = 0; i < args.size() - 4; i++) {
      searchArgs.add(args.get(i));
    }

    for (String arg : searchArgs) {
      if (fileSystem.goToPath(arg) == false) {
        super.addNewError(arg + " path does not exist\n");
        break;
      }

      String path = "";
      if (fileSystem.getCurrentDir() != fileSystem.getRoot()) {
        path = fileSystem.getCurrentDir().getName();
      }
      output += arg + ":\n";
      output = dirSearch(fileSystem.getCurrentDir(), output,
          searchName.substring(1, searchName.length() - 1), mockNode, path) + "\n";
      fileSystem.setCurrentDir(curDir);

    }

    return output.substring(0, output.length() - 1);

  }

  /**
   * Searches a single directory for files or directories that matches a given name and returns the
   * output for that directory search
   * 
   * @param dir - the directory to search
   * @param output - the final output string which would be appended onto
   * @param name - the desired name to match searching files or directories
   * @param mock - Node that represents wether serach if specified for files or directoires
   * @param path - retains the relative path for found file or directories to searching directory
   *        (dir)
   * @return output - the found directories or files
   */

  public String dirSearch(Directory dir, String output, String name, Node mock, String path) {
    ArrayList<Node> children = dir.getChildren();

    for (Node child : children) {
      String childPath = path + "/" + child.getName();

      if (child.getName().equals(name) && child.getClass() == mock.getClass()) {
        output += childPath + "\n";
      }

      if (child.getClass() == Directory.class) {
        output = dirSearch((Directory) child, output, name, mock, childPath);
      }

    }
    return output;
  }

  /**
   * Checks for invlaid parameters and returns the specified type search of either files or
   * directories based on input if valid
   * 
   * @param args - List of arguments/parameters
   * @return mockNode - This node type specifies the search type and is null for inlvaid parameters
   */

  private Node checkParamsAndMockNode(ArrayList<String> args) {
    Node mockNode = new Node();
    if (!args.get(args.size() - 2).equals("-name")) {
      super.addNewError("Invalid argument(s)");
      return null;
    }

    if (args.get(args.size() - 3).equals("f")) {
      mockNode = new File("mock");
    } else if (args.get(args.size() - 3).equals("d")) {
      mockNode = new Directory("mock");
    } else {
      super.addNewError("Invalid argument(s)");
      return null;
    }

    if (!args.get(args.size() - 4).equals("-type")) {
      super.addNewError("Invalid argument(s)");
      return null;
    }

    String searchName = args.get(args.size() - 1);
    if (searchName.length() < 3) {
      super.addNewError("Invalid input for parameter name");
      return null;
    } else if (!searchName.startsWith("\"") || !searchName.endsWith("\"")) {
      super.addNewError("name input should be wrapped in quotations");
      return null;
    }

    return mockNode;

  }
}
