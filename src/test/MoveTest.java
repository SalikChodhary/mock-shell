package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Move;
import error.StandardError;
import filesystem.Directory;
import filesystem.File;
import mockobjects.MockFileSystem;

public class MoveTest {

  public MockFileSystem fs;
  public MockFileSystem fsExpected;
  private Move mv;
  private ArrayList<String> args;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    fsExpected = new MockFileSystem(MockFileSystem.Environment.MOCK);
    mv = new Move();
    mv.setFileSystem(fs);
    StandardError.allErrors = new ArrayList<String>();
    args = new ArrayList<String>();
  }

  @Test
  public void testWithInvalidPath() {

    args.add("invalid/path");
    args.add("invalid/path2");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testEmptyDir() {
    fsExpected.goToChildDirOfCurrentDir("dir3");
    fsExpected.removeCurrentDirectory();
    fsExpected.goToPath("/dir1");
    Directory dirToBeAdded = new Directory("dir3");
    fsExpected.getCurrentDir().addChild(dirToBeAdded);
    fsExpected.goToPath("/");


    args.add("dir3");
    args.add("dir1");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testNonEmptyDir() {
    fsExpected.goToPath("dir1");
    Directory dir1 = fsExpected.getCurrentDir();
    fsExpected.removeCurrentDirectory();
    fsExpected.goToPath("/dir2");
    fsExpected.getCurrentDir().addChild(dir1);
    fsExpected.goToPath("/");

    args.add("dir1");
    args.add("dir2");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testFileToDir() {
    fsExpected.goToPath("dir1");
    File file2 = fsExpected.getCurrentDir().getFileChildByName("file2");
    fsExpected.getCurrentDir().getChildren().remove(file2);
    fsExpected.goToPath("/dir2");
    fsExpected.getCurrentDir().addChild(file2);
    fsExpected.goToPath("/");

    args.add("dir1/file2");
    args.add("dir2");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testOverwriteFile() {
    fsExpected.goToPath("/dir1");
    fsExpected.getCurrentDir().getFileChildByName("file2").setContents("MOCK: new file2 contents");
    fsExpected.goToPath("/");


    File fileToBeAdded2 = new File("file2");
    fileToBeAdded2.setContents("MOCK: new file2 contents");

    fs.getCurrentDir().addChild(fileToBeAdded2);
    fs.goToPath("/");

    args.add("file2");
    args.add("dir1");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testParenttoChild() {

    args.add("dir2/dir4");
    args.add("dir2");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testMoveParent() {

    fs.goToPath("dir2/dir4");
    args.add("/dir2");
    args.add("/dir3");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();
    fs.goToPath("/");

    assertEquals(fsExpected, fs);
  }

  @Test
  public void testParenttoChild2() {

    args.add("/");
    args.add("dir1");
    mv.setFileSystem(fs);
    mv.setArgs(args);
    mv.run();

    assertEquals(fsExpected, fs);
  }

}
