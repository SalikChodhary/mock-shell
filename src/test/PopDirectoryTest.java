package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.PopDirectory;
import error.StandardError;
import mockobjects.MockFileSystem;
import mockobjects.MockStack;
import mockobjects.MockStack.Environment;

public class PopDirectoryTest {
  public MockFileSystem fs;
  public MockStack stack;
  private PopDirectory pop;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    stack = new MockStack(Environment.MOCK);
    // stack = ["/dir2", "/", "/dir2/dir4"]
    pop = new PopDirectory(stack);
    StandardError.allErrors = new ArrayList<String>();
  }

  @Test
  public void validPath() {

    pop.setFileSystem(fs);
    pop.run();

    assertEquals(fs.dir2, fs.getCurrentDir());
  }

  @Test
  public void emptyStack() {
    pop.setFileSystem(fs);
    pop.run();
    pop.run();
    pop.run();
    pop.run();
    pop.run();

    assertEquals(fs.dir4, fs.getCurrentDir());
  }

}
