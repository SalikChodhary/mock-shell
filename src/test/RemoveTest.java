package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import commands.Remove;
import mockobjects.MockFileSystem;

public class RemoveTest {
  private Remove rm;
  private ArrayList<String> args;
  private MockFileSystem fs;
  private MockFileSystem fs2;

  @Before
  public void setUp() throws Exception {
    rm = new Remove();
    args = new ArrayList<>();
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    fs2 = new MockFileSystem(MockFileSystem.Environment.MOCK);
    rm.setFileSystem(fs);
  }

  @Test
  public void testRoot() {
    args.add("/");
    rm.setArgs(args);

    assertEquals(rm.run(), null);
    assertEquals(fs, fs2);
  }

  @Test
  public void testInvalidPath() {
    args.add("invalid/path");
    rm.setArgs(args);

    assertEquals(rm.run(), null);
    assertEquals(fs, fs2);
  }

  @Test
  public void testDir() {
    args.add("dir1");
    rm.setArgs(args);

    fs2.goToPath("dir1");
    fs2.removeCurrentDirectory();
    fs2.goToPath("/");

    assertEquals(rm.run(), null);
    assertEquals(fs, fs2);
  }

  @Test
  public void testFile() {
    args.add("dir1/file2");
    rm.setArgs(args);

    assertEquals(rm.run(), null);
    assertEquals(fs, fs2);
  }

}
