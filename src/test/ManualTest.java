package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import commands.Manual;

public class ManualTest {
  private Manual man;
  private ArrayList<String> args;

  @Before
  public void setUp() throws Exception {
    man = new Manual();
    args = new ArrayList<>();
  }

  @Test
  public void testWrongArg() {
    args.add("mann");
    man.setArgs(args);
    assertEquals(null, man.run());
  }

  @Test
  public void testArgMan() {
    String exp = "Name\n" + "    man - Print documentation for commands\n" + "    \n" + "SYNOPSIS\n"
        + "    man COMMAND1 [COMMAND2 ...]\n" + "    \n" + "DESCRIPTION\n"
        + "    Print documentation for COMMAND1 ( COMMAND2...)";
    args.add("man");
    man.setArgs(args);
    assertEquals(exp, man.run());
  }

  @Test
  public void testArgTree() {
    String exp = "Name\n" + "    pwd - Display the filesystem\n" + "    \n" + "SYNOPSIS\n"
        + "    tree \n" + "    \n" + "DESCRIPTION\n"
        + "    Prints the whole filesystem in a tree like fashion\n";
    args.add("tree");
    man.setArgs(args);
    assertEquals(exp, man.run());
  }


  @Test
  public void testArgList() {
    String exp = "Name\n" + "    ls - list directory contents\n" + "    \n" + "SYNOPSIS\n"
        + "    ls [-R] [PATH…]\n" + "    \n" + "DESCRIPTION\n"
        + "If -R is used, will recursively list all subdirectories\n"
        + "    If no paths are given, print the contents (gile or directory) of the current\n"
        + "       directory, with a new line following each of the content (file or directory).\n"
        + "    Otherwise, for each path p, the order listed:  \n"
        + "        If p specifies a file, print p\n"
        + "        If p specifies a directory, print p, a colon, then the contents of that\n"
        + "           directory, then an extra new line.\n"
        + "        If p does not exist, print a suitable message";
    args.add("ls");
    man.setArgs(args);
    assertEquals(exp, man.run());
  }

  @Test
  public void testArgMakeDirectory() {
    String exp = "Name\n" + "    mkdir - make directories\n" + "    \n" + "SYNOPSIS\n"
        + "    mkdir DIRECTORY1 [DIRECTORY2 ...]\n" + "    \n" + "DESCRIPTION\n"
        + "    Create  DIRECTORY1 then create [DIRECTORY2 ...]\n"
        + "    If creating DIRECTORY1 has an issue do not create [DIRECTORY2 ...] or DIRECTORY1\n"
        + "    If creating DIRECTORY1 is succesfull but creating [DIRECTORY2 ...]\n"
        + "        has an issue, only create DIRECTORY1...\n";
    args.add("mkdir");
    man.setArgs(args);
    assertEquals(exp, man.run());
  }


}
