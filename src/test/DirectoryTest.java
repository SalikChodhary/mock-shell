package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import filesystem.Directory;
import filesystem.File;
import filesystem.Node;

public class DirectoryTest {

  Directory testDir;
  Directory childDir;
  File childFile;

  @Before
  public void setUp() throws Exception {
    childDir = new Directory("childDir");
    childFile = new File("childFile");
    testDir = new Directory("parent");
    testDir.addChild(childFile);
    testDir.addChild(childDir);
  }

  @Test
  public void testAddChild() {
    Directory newDir = new Directory("newDir");
    Directory newChild = new Directory("newChild");
    File newChild2 = new File("newFile");

    assertTrue(newDir.addChild(newChild));
    assertTrue(newDir.addChild(newChild2));

    assertEquals(2, newDir.getChildren().size());
  }

  @Test
  public void testHasChildAlreadyExists() {
    Node testNode = testDir.getChildren().get(0);

    assertTrue(testDir.hasChild(testNode));
  }

  @Test
  public void testHasChildDoesNotExist() {
    Directory invalidChild = new Directory("invalid");

    assertFalse(testDir.hasChild(invalidChild));
  }

  @Test
  public void testHasChildAlreadyExistsWithStringParameter() {
    assertTrue(testDir.hasChild("childDir"));
  }

  @Test
  public void testHasChildDoesNotExistWithStringParameter() {
    assertFalse(testDir.hasChild("invalid"));
  }

  @Test
  public void testGetChildFileByNameValidChild() {
    assertEquals(childFile, testDir.getFileChildByName("childFile"));
  }

  @Test
  public void testGetChildFileByNameInvalidChild() {
    assertEquals(null, testDir.getFileChildByName("invalidFile"));
  }

  @Test
  public void testGetChildDirByNameValidChild() {
    assertEquals(childDir, testDir.getDirChildByName("childDir"));
  }

  @Test
  public void testGetChildDirByNameInvalidChild() {
    assertEquals(null, testDir.getDirChildByName("invalidDir"));
  }
}
