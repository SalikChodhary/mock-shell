package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import commands.PrintWorkingDirectory;
import mockobjects.MockFileSystem;


public class PrintWorkingDirectoryTest {

  private PrintWorkingDirectory pwd;
  private ArrayList<String> args;
  private MockFileSystem fs;

  @Before
  public void setUp() throws Exception {
    pwd = new PrintWorkingDirectory();
    args = new ArrayList<>();
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    pwd.setFileSystem(fs);
  }

  @Test
  public void testRoot() {
    pwd.setArgs(args);
    assertTrue(pwd.run().compareTo("/") == 0);
  }

  @Test
  public void testInDir() {
    pwd.setArgs(args);
    fs.goToChildDirOfCurrentDir("dir1");
    assertTrue(pwd.run().compareTo("/dir1") == 0);
  }

}
