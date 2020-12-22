package test;

import static org.junit.Assert.*;
import org.junit.Test;
import commands.Command;
import helpers.Pair;
import interfaces.IRedirection;
import redirection.Redirection;

public class RedirectionTest {
  Pair<Pair<IRedirection.RedirectionType, String>, String[]> res;
  Command pseudo = new Command();

  @Test
  public void testValidOverWriteRedirection() {
    String input = "cat someValidFile > anotherValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(IRedirection.RedirectionType.OVERWRITE, res.first.first);
    assertTrue(res.first.second.equals("anotherValidFile"));
    assertEquals(2, res.second.length);

    assertTrue(res.second[0].equals("cat"));
    assertTrue(res.second[1].equals("someValidFile"));
  }

  @Test
  public void testValidAppendRedirection() {
    String input = "cat someValidFile >> anotherValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(IRedirection.RedirectionType.APPEND, res.first.first);
    assertTrue(res.first.second.equals("anotherValidFile"));
    assertEquals(2, res.second.length);

    assertTrue(res.second[0].equals("cat"));
    assertTrue(res.second[1].equals("someValidFile"));
  }

  @Test
  public void testCommandWithNoRedirectionSupport() {
    String input = "cd someValidFile >> anotherValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(IRedirection.RedirectionType.NONE, res.first.first);
    assertTrue(res.first.second.equals("anotherValidFile"));
    assertEquals(2, res.second.length);

    assertTrue(res.second[0].equals("cd"));
    assertTrue(res.second[1].equals("someValidFile"));
  }

  @Test
  public void testValidRedirectionWithoutSpacesAroundArrows() {
    String input = "cat someValidFile>>anotherValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(IRedirection.RedirectionType.APPEND, res.first.first);
    assertTrue(res.first.second.equals("anotherValidFile"));
    assertEquals(2, res.second.length);

    assertTrue(res.second[0].equals("cat"));
    assertTrue(res.second[1].equals("someValidFile"));
  }

  @Test
  public void testInvalidRedirection() {
    String input = "cat someValidFile>>another >> ValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(null, res);
  }

  @Test
  public void testNoRedirection() {
    String input = "cat someValidFile";
    String inputArr[] = input.split(" ");

    res = Redirection.getRedirectionData(input, inputArr, pseudo);

    assertEquals(IRedirection.RedirectionType.NONE, res.first.first);
    assertEquals(null, res.first.second);
    assertEquals(2, res.second.length);

    assertTrue(res.second[0].equals("cat"));
    assertTrue(res.second[1].equals("someValidFile"));
  }

}
