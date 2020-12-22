package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import parser.Parser;
import org.junit.Before;
import org.junit.Test;
import error.StandardError;

public class ParserTest {

  Parser parser;

  @Before
  public void setUp() throws Exception {
    parser = new Parser();
    StandardError.allErrors = new ArrayList<String>();
  }

  @Test
  public void testInvalidCommand() {
    parser.parse("invaldCommand args");

    assertTrue(StandardError.allErrors.get(0).equals("Err: invalid command"));
  }

  @Test
  public void testValidCommand() {
    parser.parse("cd /");

    assertEquals(0, StandardError.allErrors.size());
  }

  @Test
  public void testExtraWhiteSpace() {
    parser.parse("cd          /        ");

    assertEquals(0, StandardError.allErrors.size());
  }

  @Test
  public void testNoSpace() {
    parser.parse("cd/");

    assertTrue(StandardError.allErrors.size() > 0);
  }
}
