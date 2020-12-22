package commands;

import java.net.*;
import helpers.Output;
import interfaces.IRedirection;
import java.io.*;

/**
 * Provides the ability to perform the "curl" command.
 */
public class Curl extends Command {
  /**
   * Runs the "curl" command.
   * 
   * @return String - any valid user output
   */
  public String run() {
    try {
      URL urlToFetch = new URL(this.getArgs().get(0));
      URLConnection connection = urlToFetch.openConnection();
      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String latestRead;
      StringBuilder out = new StringBuilder("");

      while ((latestRead = input.readLine()) != null) {
        out.append(latestRead);
        out.append("\n");
      }

      String path[] = this.getArgs().get(0).split("/");
      String file = path[path.length - 1];
      file = file.replace(".", "");
      Output.redirect(super.getFileSystem(), out.toString(), file,
          IRedirection.RedirectionType.OVERWRITE);
    } catch (Exception e) {
      super.addNewError("Err: curl: check URL");
    }

    return null;
  }
}
