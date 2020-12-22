package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.History;
import commands.SaveJShell;
import error.StandardError;
import java.io.File;

public class SaveJShellTest {
  History history;
  ArrayList<String> args;
  SaveJShell save;

  @Before
  public void setUp() throws Exception {
    history = new History();
    args = new ArrayList<String>();
    save = new SaveJShell(history);
    StandardError.allErrors = new ArrayList<String>();
  }

  @Test
  public void testValidCase() {
    File f = new File("testfile");
    args.add("testfile");
    save.setArgs(args);
    save.run();

    assertEquals(0, StandardError.allErrors.size());
    assertTrue(f.exists() && !f.isDirectory());
  }

}
