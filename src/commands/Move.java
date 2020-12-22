package commands;

import error.ErrorChecker;
import filesystem.Directory;
import filesystem.File;
import interfaces.IFileSystem;

/**
 * This class is for the Move command which moves Directories or Files
 */
public class Move extends Command {

  /**
   * Executes the move command
   * 
   * @return always returns null as it does not return a string
   */
  public String run() {
    int i = 0;
    String fileName = "";
    String parent = "";
    IFileSystem fileSystem = super.getFileSystem();
    Directory startingDir = fileSystem.getCurrentDir();
    File file1 = null;
    Directory dir1 = null;
    String arg1Path = "";
    String arg2Path = "";
    String node2Name = null;
    Directory node2Parent = null;
    if (ErrorChecker.hasConsecutiveSlash(this.getArgs().get(0))
        || ErrorChecker.hasConsecutiveSlash(this.getArgs().get(1)))
      return null;
    if (this.getArgs().get(0).compareTo("/") == 0) {
      super.addNewError("Err: mv: cannot move a parent directory to a child");
      return null;
    }
    parser(startingDir, fileSystem, file1, dir1, arg1Path, arg2Path, node2Name, node2Parent, i,
        fileName, parent);

    return null;
  }

  /**
   * Parses the arguments and sent to the caller(Copy)
   * 
   * @param startingDir the starting directory
   * @param fileSystem the file system interface
   * @param file1 will be set if argument1 is a file
   * @param dir1 will be set if argument1 is a directory
   * @param arg1Path will be set to the path of argument1
   * @param arg2Path will be set to the path of argument1
   * @param node2Name will be set to the name of argument2
   * @param node2Parent will be set to the parent Directory of argument2
   * @param i counter for the arguments
   * @param fileName each argument's filename
   * @param parent each argument's filename
   */
  private void parser(Directory startingDir, IFileSystem fileSystem, File file1, Directory dir1,
      String arg1Path, String arg2Path, String node2Name, Directory node2Parent, int i,
      String fileName, String parent) {
    for (String arg : this.getArgs()) {
      i++;
      if (arg.compareTo("/") == 0) {
        fileName = "/";
        parent = "/";
      } else {
        String[] path = arg.split("/");
        fileName = path[path.length - 1];
        parent = path.length > 1 ? arg.substring(0, arg.length() - fileName.length() - 1) : "";
        if (path.length == 2 && path[0].isEmpty() && arg.startsWith("/"))
          parent = "/";
      }
      if (parent.compareTo("") == 0) {
        if (fileName.compareTo(".") == 0) {
          String temp[] = fileSystem.getPath().split("/");
          fileName = temp[temp.length - 1];
          parent = "..";
        }
      }
      if (fileSystem.goToPath(parent)) {
        if (i == 1) {
          dir1 = fileSystem.getCurrentDir().getDirChildByName(fileName);
          file1 = fileSystem.getCurrentDir().getFileChildByName(fileName);
          arg1Path = parent.compareTo("/") == 0 ? fileSystem.getPath() + fileName
              : fileSystem.getPath() + "/" + fileName;;
        } else {
          node2Parent =
              parent.compareTo("/") == 0 ? fileSystem.getRoot() : fileSystem.getCurrentDir();
          arg2Path = fileSystem.getPath();
          node2Name = fileName;
        }
      }
      fileSystem.setCurrentDir(startingDir);
    }
    move(dir1, file1, arg1Path, node2Parent, node2Name, arg2Path);
  }

  /**
   * Checks arguments exist/ are invalid and makes the right function call
   * 
   * @param dir1 directory to be moved
   * @param file1 file to be moved
   * @param arg1Path path of Node being moved
   * @param node2Parent parent directory of Node being moved to
   * @param node2Name name of Node being moved to
   * @param arg2Path the path moved copied to
   * 
   */
  private void move(Directory dir1, File file1, String arg1Path, Directory node2Parent,
      String node2Name, String arg2Path) {
    String currentPath = super.getFileSystem().getPath();
    if (dir1 != null) {
      if (node2Parent == null) {
        super.addNewError("Err: mv: " + this.getArgs().get(1) + " is not a valid path");
        return;
      }
      if (node2Parent.getFileChildByName(node2Name) != null) {
        super.addNewError("Err: mv: can't copy directory into a file");
        return;
      }
      if (dir1.getParent() == node2Parent.getDirChildByName(node2Name)
          || dir1.getParent().getName() == "/" && "/" == node2Name)
        return;
      if (arg2Path.contains(arg1Path + "/") || arg2Path == arg1Path) {
        super.addNewError("Err: mv: cannot move a parent directory to a child");
        return;
      }
      if (dir1 == super.getFileSystem().getCurrentDir() || currentPath.contains(arg1Path + "/")) {
        super.addNewError("Err: mv: cannot move current directory or it's parents");
        return;
      }
      dirToDir(dir1, node2Parent, node2Name);
    } else if (file1 != null) {
      if (node2Parent == null) {
        super.addNewError("Err: mv: " + this.getArgs().get(1) + " is not a valid path");
        return;
      }
      if (node2Parent.getDirChildByName(node2Name) != null)
        fileToDir(file1, node2Parent.getDirChildByName(node2Name), file1.getName());
      else
        fileToDir(file1, node2Parent, node2Name);
    } else {
      super.addNewError("Err: mv: " + this.getArgs().get(0) + " is not a valid path");
      return;
    }
  }

  /**
   * movies a directory to another directory
   * 
   * @param DirOne directory to be moved
   * @param DirTwoParent parent of directory being moved to
   * @param newDirName name of directory being moved to
   */
  private void dirToDir(Directory DirOne, Directory DirTwoParent, String newDirName) {
    Directory newDir = DirTwoParent.getDirChildByName(newDirName);
    if (DirTwoParent == super.getFileSystem().getRoot() && newDirName == "/") {
      newDir = super.getFileSystem().getRoot();
    }
    if (newDir != null) {
      if (newDir.getDirChildByName(DirOne.getName()) != null) {
        if (newDir.getDirChildByName(DirOne.getName()).getChildren().isEmpty()) {
          DirOne.getParent().getChildren().remove(DirOne);
          newDir.addChild(DirOne);
          return;
        } else {
          super.addNewError(
              "Err: mv: " + newDir.getName() + "/" + DirOne.getName() + " is not empty");
          return;
        }
      }
      DirOne.getParent().getChildren().remove(DirOne);
      newDir.addChild(DirOne);
    } else {
      if (ErrorChecker.checkValidDirFileName(newDirName)) {
        DirOne.getParent().getChildren().remove(DirOne);
        Directory dir = new Directory(newDirName);
        DirTwoParent.addChild(dir);
        dir.addChild(DirOne);
      } else
        super.addNewError("Err: mv: " + newDirName + " is not a valid Directory Name");
    }
  }

  /**
   * movies a file to a directory
   * 
   * @param orginalFile file to be moved
   * @param dirTwo directory to be moved to
   * @param newFileName name of file being moved to
   */
  private void fileToDir(File orginalFile, Directory dirTwo, String newFileName) {
    File newFile = dirTwo.getFileChildByName(newFileName);

    if (newFile != null) {
      newFile.setContents(orginalFile.getContents());
      orginalFile.getParent().getChildren().remove(orginalFile);
    } else {
      if (ErrorChecker.checkValidDirFileName(newFileName)) {
        orginalFile.getParent().getChildren().remove(orginalFile);
        orginalFile.setName(newFileName);
        dirTwo.addChild(orginalFile);
      } else
        super.addNewError(
            "Err: mv: " + newFileName + " is not a valid File Name (Cannot create new Directory)");
    }
  }
}
