package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Echo;

public class EchoTest {

  private Echo echo;
  private ArrayList<String> args;


  @Before
  public void setUp() throws Exception {
    echo = new Echo();
    args = new ArrayList<String>();
  }

  @Test
  public void testValidString() {
    echo.setRawUserInput("echo \"Echo Test\"");
    echo.setArgs(args);
    String res = echo.run();

    assertTrue(res.compareTo("Echo Test") == 0);
  }

  @Test
  public void testInvalidStringUnwrapped() {
    echo.setRawUserInput("echo \"Echo Test");
    echo.setArgs(args);
    String res = echo.run();

    assertEquals(res, null);
  }

  @Test
  public void testInvalidStringQuoteInString() {
    echo.setRawUserInput("echo \"Echo \" Test\"");
    echo.setArgs(args);
    String res = echo.run();

    assertEquals(res, null);
  }
}
