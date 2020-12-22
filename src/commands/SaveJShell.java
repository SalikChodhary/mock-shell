package commands;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import error.ErrorChecker;
import filesystem.FileSystem;
import stack.DirStack;

/**
 * Save state of shell command
 */
public class SaveJShell extends Command {
  /**
   * Holds the instance of the current state of history in parser
   */
  private History historyToSave;

  /**
   * Getter for historyToSave
   * 
   * @return historyToSave
   */
  public History getHistoryToSave() {
    return historyToSave;
  }

  /**
   * Setter for historyToSave
   * 
   * @param historyToSave - new state of historyToSave
   */
  public void setHistoryToSave(History historyToSave) {
    this.historyToSave = historyToSave;
  }

  /**
   * Constructor for SaveJShell, initalizes history
   * 
   * @param history - state of history at the time of calling
   */
  public SaveJShell(History history) {
    this.setHistoryToSave(history);
  }

  /**
   * Provides the ability to run the "saveJShell" command
   * 
   * @return String null
   */
  public String run() {
    String fileName = super.getArgs().get(0);

    if (!ErrorChecker.checkValidDirFileName(fileName))
      return null;

    try {

      FileOutputStream fout = new FileOutputStream(fileName);
      ObjectOutputStream objout = new ObjectOutputStream(fout);

      FileSystem fs = FileSystem.getInstance();
      DirStack dirStack = DirStack.getInstance();
      objout.writeObject(fs);
      objout.writeObject(dirStack);
      objout.writeObject(this.getHistoryToSave());

      objout.close();
      fout.close();
    } catch (FileNotFoundException f) {
      super.addNewError("Err: loadJShell: save file does not exist");
    } catch (IOException i) {
      super.addNewError("Err: saveJShell: internal error: IOException");
    }
    return null;
  }
}
