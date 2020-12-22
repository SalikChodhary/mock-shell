package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.History;
import commands.LoadJShell;
import error.StandardError;

public class LoadJShellTest {
  History history;
  ArrayList<String> args;
  LoadJShell load;

  @Before
  public void setUp() throws Exception {
    history = new History();
    args = new ArrayList<String>();
    load = new LoadJShell(history);
    StandardError.allErrors = new ArrayList<String>();
  }

  @Test
  public void testValidCase() {
    args.add("testfile");
    load.setArgs(args);
    load.run();

    assertEquals(0, StandardError.allErrors.size());
  }

  @Test
  public void testInvalidFile() {
    args.add("invalidfile");
    load.setArgs(args);
    load.run();

    assertEquals("Err: loadJShell: save file does not exist", StandardError.allErrors.get(0));
  }

}
