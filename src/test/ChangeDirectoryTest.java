package test;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import commands.ChangeDirectory;
import error.StandardError;
import filesystem.Directory;
import mockobjects.MockFileSystem;

public class ChangeDirectoryTest {

  public MockFileSystem fs;
  private ChangeDirectory cd;
  private ArrayList<String> args;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    cd = new ChangeDirectory();
    StandardError.allErrors = new ArrayList<String>();
    args = new ArrayList<String>();
  }

  @Test
  public void testWithInvalidPath() {
    Directory startingDir = fs.getCurrentDir();

    args.add("invalid/path");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(startingDir, fs.getCurrentDir());
  }

  @Test
  public void testCdRootFromRoot() {
    args.add("/");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(fs.getRoot(), fs.getCurrentDir());
  }

  @Test
  public void testFullPath() {
    args.add("/dir2/dir4");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(fs.dir4, fs.getCurrentDir());
  }

  @Test
  public void testRelativePath() {
    args.add("dir1");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(fs.dir1, fs.getCurrentDir());
  }

  @Test
  public void testParentDirShorthand() {
    args.add("/dir2/dir4/../..");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(fs.getRoot(), fs.getCurrentDir());
  }

  @Test
  public void testCurrentDirShorthand() {
    args.add("/dir2/dir4/./.");
    cd.setFileSystem(fs);
    cd.setArgs(args);
    cd.run();

    assertEquals(fs.dir4, fs.getCurrentDir());
  }
}
