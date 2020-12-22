package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import filesystem.Directory;
import filesystem.FileSystem;

public class FileSystemTest {
  FileSystem fs;

  @Before
  public void setUp() throws Exception {
    fs = FileSystem.getInstance();
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fileSystemInstance");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }


  @Test
  public void testSingleton() {
    assertEquals(fs, FileSystem.getInstance());
  }

  @Test
  public void testGoToChildDirOfCurrDirValidName() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);


    assertTrue(fs.goToChildDirOfCurrentDir("dir1"));
    assertEquals(dir1, fs.getCurrentDir());
  }

  @Test
  public void testGoToChildDirOfCurrDirInvalidName() {
    assertTrue(!fs.goToChildDirOfCurrentDir("invalid/path"));
  }

  @Test
  public void testGoToParentDirOfCurrDir() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);
    fs.goToChildDirOfCurrentDir("dir1");

    assertTrue(fs.goToParentOfCurrentDir());
    assertEquals(fs.getRoot(), fs.getCurrentDir());
  }

  @Test
  public void testGoToParentDirOfCurrDirFromRoot() {
    assertTrue(!fs.goToParentOfCurrentDir());
  }

  @Test
  public void testGoToPathRelativePath() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);


    assertTrue(fs.goToPath("dir1"));
    assertEquals(dir1, fs.getCurrentDir());
  }

  @Test
  public void testGoToPathAbsolutePath() {
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    fs.getCurrentDir().addChild(dir1);
    dir1.addChild(dir2);


    assertTrue(fs.goToPath("/dir1/dir2"));
    assertEquals(dir2, fs.getCurrentDir());
  }

  @Test
  public void testGoToPathWithParentNotation() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);


    assertTrue(fs.goToPath("/dir1/.."));
    assertEquals(fs.getRoot(), fs.getCurrentDir());
  }

  @Test
  public void testGoToPathWithNotation() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);


    assertTrue(fs.goToPath("/./dir1"));
    assertEquals(dir1, fs.getCurrentDir());
  }

  @Test
  public void testGetPath() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);


    assertTrue(fs.goToPath("dir1"));
    assertTrue(fs.getPath().compareTo("/dir1") == 0);
  }

  @Test
  public void testRemoveCurrentDirectoryInvalidCase() {
    assertTrue(!fs.removeCurrentDirectory());
  }

  @Test
  public void testRemoveCurrentDirectoryValidCase() {
    Directory dir1 = new Directory("dir1");
    fs.getCurrentDir().addChild(dir1);
    fs.goToPath("dir1");

    assertTrue(fs.removeCurrentDirectory());
    assertEquals(fs.getRoot(), fs.getCurrentDir());
    assertEquals(0, fs.getCurrentDir().getChildren().size());
  }
}
