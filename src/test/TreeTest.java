package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Tree;
import error.StandardError;
import mockobjects.MockFileSystem;


public class TreeTest {

  public MockFileSystem fs;
  private Tree tree;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    tree = new Tree();
    StandardError.allErrors = new ArrayList<String>();
  }

  @Test
  public void testAtRoot() {
    String expected = "/\n" + "     dir1\n" + "          file2\n" + "     dir2\n"
        + "          dir4\n" + "     root_file1\n" + "     dir3\n";

    tree.setFileSystem(fs);

    assertEquals(expected, tree.run());
  }

  @Test
  public void testWithRemovedDirAtRoot() {
    String expected = "/\n" + "     dir1\n" + "          file2\n" + "     dir2\n"
        + "          dir4\n" + "     root_file1\n";
    fs.goToChildDirOfCurrentDir("dir3");
    fs.removeCurrentDirectory();
    tree.setFileSystem(fs);

    assertEquals(expected, tree.run());
  }

  @Test
  public void testAtDirNotRoot() {
    String expected = "/\n" + "     dir1\n" + "          file2\n" + "     dir2\n"
        + "          dir4\n" + "     root_file1\n" + "     dir3\n";

    fs.goToChildDirOfCurrentDir("dir2");
    fs.goToChildDirOfCurrentDir("dir4");
    tree.setFileSystem(fs);

    assertEquals(expected, tree.run());
  }

  @Test
  public void testWithDirAndChildrenRemoved() {
    String expected =
        "/\n" + "     dir2\n" + "          dir4\n" + "     root_file1\n" + "     dir3\n";

    fs.goToChildDirOfCurrentDir("dir1");
    fs.getCurrentDir().getChildren().remove(fs.getCurrentDir().getFileChildByName("file2"));
    fs.removeCurrentDirectory();
    tree.setFileSystem(fs);

    assertEquals(expected, tree.run());
  }

}
