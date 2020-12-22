package test;

import static org.junit.Assert.*;
import org.junit.Test;
import error.ErrorChecker;

public class ErrorCheckerTest {

  @Test
  public void testSingleWordCommandNumberOfArgs() {
    String[] inputArr = {"exit"};

    assertEquals(ErrorChecker.checkNumberOfArgs(inputArr), true);
  }

  @Test
  public void testDoubleWordCommandNumberOfArgs() {
    String[] inputArr = {"history", "10"};

    assertEquals(ErrorChecker.checkNumberOfArgs(inputArr), true);
  }

  @Test
  public void testTripleWordCommandNumberOfArgs() {
    String[] inputArr = {"mv", "a", "b"};

    assertEquals(ErrorChecker.checkNumberOfArgs(inputArr), true);
  }

  @Test
  public void testMoreWordCommandNumberOfArgs() {
    String[] inputArr = {"mkdir", "a", "b", "v", "c"};

    assertEquals(ErrorChecker.checkNumberOfArgs(inputArr), true);
  }

  @Test
  public void testValidCheckValidDirFileName() {
    String input = "sdfadfadfadfadf";

    assertEquals(ErrorChecker.checkValidDirFileName(input), true);
  }

  @Test
  public void testInvalidCheckValidDirFileName() {
    String input = "sdfadfadfa@dfadf";

    assertEquals(ErrorChecker.checkValidDirFileName(input), false);
  }

  @Test
  public void testInvalidCheckValidDirFileName2() {
    String input = "sdfadfadf{a#df}adf";

    assertEquals(ErrorChecker.checkValidDirFileName(input), false);
  }

  @Test
  public void testValidCheckValidString() {
    String input = "\"sdfadfadfadfadf\"";

    assertEquals(ErrorChecker.checkValidString(input), true);
  }

  @Test
  public void testInvalidCheckValidString() {
    String input = "sdfadfadfadfadf\"";

    assertEquals(ErrorChecker.checkValidString(input), false);
  }

  @Test
  public void testHasHasConsecutiveSlash() {
    String input = "asdf//asdf";

    assertEquals(ErrorChecker.hasConsecutiveSlash(input), true);
  }

  @Test
  public void testNotHasConsecutiveSlash() {
    String input = "asdf/asdf";

    assertEquals(ErrorChecker.hasConsecutiveSlash(input), false);
  }
}
