package parser;

import commands.*;
import error.ErrorChecker;
import error.StandardError;
import filesystem.FileSystem;
import helpers.Output;
import helpers.Pair;
import interfaces.IRedirection.RedirectionType;
import redirection.Redirection;
import stack.DirStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses user input and calls respective command
 */
public class Parser {
  /**
   * HashMap containing all the commands and theyre constructors
   */
  private HashMap<String, Command> commands;
  /**
   * History of all entered commands
   */
  private History history;

  /**
   * Setter for history 
   * 
   * @param history - new history
   */
  public void setHistory(History history) {
    this.history = history;
  }

  /**
   * Constructs parser object and initializes commands hashmap
   */
  public Parser() {
    this.history = new History();

    this.commands = new HashMap<String, Command>();
    this.commands.put("exit", new Exit());
    this.commands.put("mkdir", new MakeDirectory());
    this.commands.put("cd", new ChangeDirectory());
    this.commands.put("pwd", new PrintWorkingDirectory());
    this.commands.put("", new Command());
    this.commands.put("popd", new PopDirectory(DirStack.getInstance()));
    this.commands.put("pushd", new PushDirectory(DirStack.getInstance()));
    this.commands.put("history", history);
    this.commands.put("cat", new Concatenate());
    this.commands.put("ls", new ListDirectory());
    this.commands.put("man", new Manual());
    this.commands.put("echo", new Echo());
    this.commands.put("curl", new Curl());
    this.commands.put("search", new Search());
    this.commands.put("cp", new Copy());
    this.commands.put("saveJShell", new SaveJShell(history));
    this.commands.put("loadJShell", new LoadJShell(history));
    this.commands.put("mv", new Move());
    this.commands.put("tree", new Tree());
    this.commands.put("rm", new Remove());
  }

  /**
   * Initializes the current command, it's args and the filesystem that it should collaborate with
   * 
   * @param inputArr - Cleansed user input
   * @param command - instance of command to be executed
   */
  private void initCommand(String[] inputArr, Command command) {
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = FileSystem.getInstance();

    for (int i = 1; i < inputArr.length; i++) {
      args.add(inputArr[i]);
    }

    command.setCurrentCommand(inputArr[0]);
    command.setArgs(args);
    command.setFileSystem(fs);
  }

  /**
   * Parse the user input into an array of tokens and run the corresponding command
   * 
   * @param input raw user input
   */
  public void parse(String input) {
    String[] inputArr = input.trim().split("\\s+");
    Command command;

    if (inputArr.length < 1)
      return;
    history.addCommandToHistory(input);
    command = this.commands.get(inputArr[0]);
    if (command == null) {
      StandardError.allErrors.add("Err: invalid command");
      return;
    }

    Pair<Pair<RedirectionType, String>, String[]> res =
        Redirection.getRedirectionData(input, inputArr, command);
    if (res == null) {
      StandardError.allErrors.add("Err: invalid redirection");
      return;
    }

    inputArr = res.second;

    if (ErrorChecker.checkNumberOfArgs(inputArr) == false)
      return;

    this.initCommand(inputArr, command);
    String cmdOutput = command.run();

    if (cmdOutput == null)
      return;
    if (res.first.first == RedirectionType.NONE)
      Output.print(cmdOutput);
    else
      Output.redirect(FileSystem.getInstance(), cmdOutput, res.first.second, res.first.first);
  }

}
