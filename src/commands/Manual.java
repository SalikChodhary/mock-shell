package commands;

import java.util.HashMap;

/**
 * Provides the ability to perform the "man" command
 */
public class Manual extends Command {
  /**
   * This hash map has entry as command(key) and it's manual(value)
   */
  HashMap<String, String> manuals = new HashMap<String, String>();
  String exit = "Name\n" + "    exit - Quit the program\n" + "    \n" + "SYNOPSIS\n" + "    exit\n"
      + "    \n" + "DESCRIPTION\n" + "    Quit the program\n";

  String pwd = "Name\n" + "    pwd - print name of current/working directory\n" + "    \n"
      + "SYNOPSIS\n" + "    pwd \n" + "    \n" + "DESCRIPTION\n"
      + "    Print the current working directory (including the whole path)\n";

  String mkdir = "Name\n" + "    mkdir - make directories\n" + "    \n" + "SYNOPSIS\n"
      + "    mkdir DIRECTORY1 [DIRECTORY2 ...]\n" + "    \n" + "DESCRIPTION\n"
      + "    Create  DIRECTORY1 then create [DIRECTORY2 ...]\n"
      + "    If creating DIRECTORY1 has an issue do not create [DIRECTORY2 ...] or DIRECTORY1\n"
      + "    If creating DIRECTORY1 is succesfull but creating [DIRECTORY2 ...]\n"
      + "        has an issue, only create DIRECTORY1...\n";

  String cd = "Name\n" + "    cd - change the working directory\n" + "    \n" + "SYNOPSIS\n"
      + "    cd DIRECTORY\n" + "    \n" + "DESCRIPTION\n"
      + "    Change directory to DIR, which may be   relative to the current directory or    \n"
      + "    may be a full path";

  String ls = "Name\n" + "    ls - list directory contents\n" + "    \n" + "SYNOPSIS\n"
      + "    ls [-R] [PATH…]\n" + "    \n" + "DESCRIPTION\n"
      + "If -R is used, will recursively list all subdirectories\n"
      + "    If no paths are given, print the contents (gile or directory) of the current\n"
      + "       directory, with a new line following each of the content (file or directory).\n"
      + "    Otherwise, for each path p, the order listed:  \n"
      + "        If p specifies a file, print p\n"
      + "        If p specifies a directory, print p, a colon, then the contents of that\n"
      + "           directory, then an extra new line.\n"
      + "        If p does not exist, print a suitable message";

  String pushd = "Name\n" + "    pushd - Pushes direcotry onto directory stack and then changes\n"
      + "       the new current working directory to DIR.\n" + "    \n" + "SYNOPSIS\n"
      + "    pushd DIRECTORY\n" + "    \n" + "DESCRIPTION\n"
      + "    Saves the current working directory by pushing onto directory stack and\n"
      + "       then changes the new current working directory to DIR. The push must be\n"
      + "       consistent as per the LIFO behavior of a stack.   The pushd command saves\n"
      + "       the old current working directory in directory stack so that it can be \n"
      + "       returned to at any time (via the popd command).  The size of the directory\n"
      + "       stack is dynamic and dependent on the pushd and the popd commands.";

  String popd = "Name\n"
      + "    popd - Pops the top entry from the directory stack, and changes the new current\n"
      + "       working to it.\n" + "    \n" + "SYNOPSIS\n" + "    popd\n" + "    \n"
      + "DESCRIPTION\n"
      + "    Remove the top entry from the directory stack, and cd into it. The removal must\n"
      + "       be consistent as per the LIFO behavior of  a stack.  The popd command\n"
      + "       removes the top\n"
      + "       most directory from the directory stack and makes it the current working \n"
      + "       directory.  If there is no directory onto the stack, then give appropriate\n"
      + "       error message.";

  String history = "Name\n" + "    history - Print out recent commands, one command per line. \n"
      + "    \n" + "SYNOPSIS\n" + "    history [number] \n" + "    \n" + "DESCRIPTION\n"
      + "    Prints two collums. The first column is numbered such that the line\n"
      + "       with the highest number is the most recent command. The most recent command\n"
      + "       is history.  The second column contains the actual command. \n"
      + "    If a number is given prints only the last last number commands typed.";

  String cat = "Name\n" + "    cat - Display the contents of files\n" + "    \n" + "SYNOPSIS\n"
      + "    cat FILE1 [FILE2 ...] \n" + "    \n" + "DESCRIPTION\n"
      + "    Display the contents of FILE1 and other files (i.e. FILE2 ....)\n"
      + "       concatenated in the shell. You may want to use three line breaks\n"
      + " to separate the contents of one file from the other file.  \n";

  String echo = "Name\n" + "    echo - write arguments to output\n" + "    \n" + "SYNOPSIS\n"
      + "    echo STRING [> OUTFILE] \n" + "or echo STRING >> OUTFILE " + "    \n" + "DESCRIPTION\n"
      + "    If OUTFILE is not provided, print STRING on the shell. Otherwise, put\n"
      + "       STRING into file OUTFILE. STRING is a string of characters surrounded by double\n"
      + "       quotation marks. This creates a new file if OUTFILE does not exists and erases\n"
      + "       the old contents if OUTFILE already exists. In either case, the only\n"
      + "       thing in OUTFILE should be STRING. When >> is used append instead of\n"
      + "       overwrite";

  String man = "Name\n" + "    man - Print documentation for commands\n" + "    \n" + "SYNOPSIS\n"
      + "    man COMMAND1 [COMMAND2 ...]\n" + "    \n" + "DESCRIPTION\n"
      + "    Print documentation for COMMAND1 ( COMMAND2...)";

  String rm = "Name\n" + "    rm -removes directory from fileSystem\n" + "    \n" + "SYNOPSIS\n"
      + "    rm DIRECTORY1 [DIRECTORY2 ...] \n" + "    \n" + "DESCRIPTION\n"
      + "    removes the directories specified and all their children\n";

  String mv = "Name\n" + "    mv -moves file or Dir at old path to new path\n" + "    \n"
      + "SYNOPSIS\n" + "    mv OLDPATH NEWPATH \n" + "    \n" + "DESCRIPTION\n"
      + "    moves content at OLDPATH to NEWPATH including all children\n";

  String cp = "Name\n" + "    cp -copies file or Dir at old path to new path\n" + "    \n"
      + "SYNOPSIS\n" + "    cp OLDPATH NEWPATH \n" + "    \n" + "DESCRIPTION\n"
      + "    copies content at OLDPATH to NEWPATH including all children\n";

  String curl = "Name\n" + "    curl -gets file at URL\n" + "    \n" + "SYNOPSIS\n"
      + "    curl URL \n" + "    \n" + "DESCRIPTION\n"
      + "    Retrieve the file at that URL and add it to the current working directory.\n";

  String saveJShell = "Name\n" + "    saveJShell -Saves program\n" + "    \n" + "SYNOPSIS\n"
      + "    saveJShell FILE1 \n" + "    \n" + "DESCRIPTION\n"
      + "    Save the current state of the program and filesystem and store it in FILE1 \n";

  String loadJShell = "Name\n" + "    loadJShell -Loads saved program\n" + "    \n" + "SYNOPSIS\n"
      + "    loadJShell FILE1 \n" + "    \n" + "DESCRIPTION\n"
      + "    Loads a state of the program and filesystem that is stored in FILE1 \n";

  String search = "Name\n" + "    Search -searches for patterns in filesystem\n" + "    \n"
      + "SYNOPSIS\n" + "    Search PATH… -type [f|d] -name pattern \n" + "    \n" + "DESCRIPTION\n"
      + "    Search at PATH... for type f for file or d for directory which have the name pattern \n";

  String tree =
      "Name\n" + "    pwd - Display the filesystem\n" + "    \n" + "SYNOPSIS\n" + "    tree \n"
          + "    \n" + "DESCRIPTION\n" + "    Prints the whole filesystem in a tree like fashion\n";



  /**
   * Initializes the HashMap used for the manual information
   */
  public Manual() {
    manuals.put("exit", exit);
    manuals.put("pwd", pwd);
    manuals.put("mkdir", mkdir);
    manuals.put("cd", cd);
    manuals.put("ls", ls);
    manuals.put("pushd", pushd);
    manuals.put("popd", popd);
    manuals.put("history", history);
    manuals.put("cat", cat);
    manuals.put("echo", echo);
    manuals.put("man", man);
    manuals.put("rm", rm);
    manuals.put("mv", mv);
    manuals.put("cp", cp);
    manuals.put("curl", curl);
    manuals.put("saveJShell", saveJShell);
    manuals.put("loadJShell", loadJShell);
    manuals.put("search", search);
    manuals.put("tree", tree);
  }

  /**
   * Displays the manual page for the command given
   * 
   * @return String - the manual for the requested command
   */
  public String run() {
    for (String arg : this.getArgs()) {
      String description = this.manuals.get(arg);
      if (description == null) {
        super.addNewError("Err: man: no documentation for " + arg);
        return null;
      }
      return description;
    }
    return null;
  }
}
