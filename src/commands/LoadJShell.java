package commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import error.ErrorChecker;
import filesystem.FileSystem;
import stack.DirStack;

/**
 * Load state of shell from previously saved data
 */
public class LoadJShell extends Command {
  /**
   * Holds the instance of the current state of history in parser
   */
  private History historyToUpdate;

  /**
   * Getter for historyToUpdate
   * 
   * @return historyToUpdate
   */
  public History getHistoryToUpdate() {
    return historyToUpdate;
  }

  /**
   * Setter for historyToUpdate
   * 
   * @param historyToUpdate - new state of historyToUpdate
   */
  public void setHistoryToUpdate(History historyToUpdate) {
    this.historyToUpdate = historyToUpdate;
  }

  /**
   * Constructor for LoadJShell, initalizes history
   * 
   * @param history - state of history at the time of calling
   */
  public LoadJShell(History history) {
    this.setHistoryToUpdate(history);
  }

  /**
   * Initiate the dir stack based on the saved data file
   * @param objin - Object Input Stream
   * @throws ClassNotFoundException 
   * @throws IOException
   */
  private void initDirStack(ObjectInputStream objin) throws ClassNotFoundException, IOException {
    DirStack currDirStack = DirStack.getInstance();
    DirStack newStack = (DirStack) objin.readObject();

    try {
      Field field = (currDirStack.getClass()).getDeclaredField("stackInstance");
      field.setAccessible(true);
      field.set(null, newStack);

      field = (currDirStack.getClass()).getDeclaredField("dirStack");
      field.setAccessible(true);
      field.set(currDirStack, newStack.dirStack);
    } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {

    }
  }

  /**
   * Provides the ability to run the "loadJShell" command
   * 
   * @return String null
   */
  public String run() {
    String fileToGet = this.getArgs().get(0);

    if (!ErrorChecker.checkValidDirFileName(fileToGet))
      return null;

    try {
      FileInputStream fin = new FileInputStream(fileToGet);
      ObjectInputStream objin = new ObjectInputStream(fin);
      @SuppressWarnings("unused")
      FileSystem fs = FileSystem.getInstance();

      fs = (FileSystem) objin.readObject();
      initDirStack(objin);
      History newHistory = (History) objin.readObject();

      this.getHistoryToUpdate().setHistory(newHistory.getHistory());
      this.getHistoryToUpdate().setNumOfCommands(newHistory.getNumOfCommands());
      objin.close();
      fin.close();
    } catch (FileNotFoundException f) {
      super.addNewError("Err: loadJShell: save file does not exist");
    } catch (IOException i) {
      super.addNewError("Err: loadJShell: internal error: IOException");
    } catch (ClassNotFoundException c) {
      super.addNewError("Err: loadJShell: internal error: ClassNotFound");
    }
    return null;
  }
}
