package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import commands.Search;
import error.StandardError;
import mockobjects.MockFileSystem;

public class SearchTest {
  public MockFileSystem fs;
  private Search ser;
  private ArrayList<String> args;

  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem(MockFileSystem.Environment.MOCK);
    ser = new Search();
    StandardError.allErrors = new ArrayList<String>();
    args = new ArrayList<String>();
  }

  @Test
  public void serDir() {
    ser.setFileSystem(fs);
    args.add("/");
    args.add("-type");
    args.add("d");
    args.add("-name");
    args.add("\"dir2\"");
    ser.setArgs(args);
    String expect = "/:\n/dir2\n";
    assertEquals(expect, ser.run());
  }

  @Test
  public void serDirDNE() {
    ser.setFileSystem(fs);
    args.add("dir2");
    args.add("-type");
    args.add("d");
    args.add("-name");
    args.add("\"dir2\"");
    ser.setArgs(args);
    String expect = "dir2:\n";
    assertEquals(expect, ser.run());
  }

  @Test
  public void serDirMultiErr() {
    ser.setFileSystem(fs);

    args.add("/");
    args.add("dir2");
    args.add("Invlaid");
    args.add("dir2");

    args.add("-type");
    args.add("d");
    args.add("-name");
    args.add("\"dir4\"");
    ser.setArgs(args);

    String expect1 = "/:\n/dir2/dir4\n";
    String expect2 = "dir2:\ndir2/dir4\n";
    String expect = expect1 + "\n" + expect2;

    assertEquals(expect, ser.run());
  }

  @Test
  public void serFile() {
    ser.setFileSystem(fs);
    args.add("/");
    args.add("-type");
    args.add("f");
    args.add("-name");
    args.add("\"root_file1\"");
    ser.setArgs(args);
    String expect = "/:\n/root_file1\n";
    assertEquals(expect, ser.run());
  }

  @Test
  public void serFileDNE() {
    ser.setFileSystem(fs);
    args.add("dir2");
    args.add("-type");
    args.add("f");
    args.add("-name");
    args.add("\"root_file1\"");
    ser.setArgs(args);
    String expect = "dir2:\n";
    assertEquals(expect, ser.run());
  }

  @Test
  public void serFileMultiErr() {
    ser.setFileSystem(fs);

    args.add("/");
    args.add("dir1");
    args.add("Invlaid");
    args.add("dir1");

    args.add("-type");
    args.add("f");
    args.add("-name");
    args.add("\"file2\"");
    ser.setArgs(args);

    String expect1 = "/:\n/dir1/file2\n";
    String expect2 = "dir1:\ndir1/file2\n";
    String expect = expect1 + "\n" + expect2;

    assertEquals(expect, ser.run());
  }

  @Test
  public void noQuotes() {
    ser.setFileSystem(fs);
    args.add("/");
    args.add("-type");
    args.add("f");
    args.add("-name");
    args.add("file2");
    ser.setArgs(args);
    String expect = "";
    assertEquals(expect, ser.run());
  }

  @Test
  public void missingParam() {
    ser.setFileSystem(fs);
    args.add("/");
    args.add("f");
    args.add("-name");
    args.add("\"file2\"");
    ser.setArgs(args);
    String expect = "";
    assertEquals(expect, ser.run());
  }

}
