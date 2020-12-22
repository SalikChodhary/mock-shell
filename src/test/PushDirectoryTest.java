package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.PushDirectory;
import error.StandardError;
import mockobjects.MockFileSystem;
import mockobjects.MockStack;
import mockobjects.MockStack.Environment;

public class PushDirectoryTest {
  public MockFileSystem fs;
  public MockStack stack;
  private PushDirectory push;
  private ArrayList<String> args;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    stack = new MockStack(Environment.MOCK);
    // stack = ["/dir2", "/", "/dir2/dir4"]
    push = new PushDirectory(stack);
    StandardError.allErrors = new ArrayList<String>();
    args = new ArrayList<String>();
  }

  @Test
  public void validPath() {
    args.add("/dir3");
    push.setArgs(args);
    push.setFileSystem(fs);
    push.run();

    assertEquals(fs.dir3, fs.getCurrentDir());
    assertEquals("/", stack.popStack());
  }

  @Test
  public void invalidPath() {
    args.add("/invalid");
    push.setFileSystem(fs);
    push.setArgs(args);
    push.run();

    assertEquals(fs.getRoot(), fs.getCurrentDir());
    assertEquals("/dir2", stack.popStack());
  }

  @Test
  public void manyArgs() {
    args.add("/dir3");
    args.add("/");
    push.setFileSystem(fs);
    push.setArgs(args);
    push.run();
    assertEquals(fs.getRoot(), fs.getCurrentDir());
    assertEquals("/dir2", stack.popStack());
  }
}
