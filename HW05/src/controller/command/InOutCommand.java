package controller.command;

import java.io.File;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;

/**
 * An abstract class that have defined common functionality to use in the save command and the load
 * command.
 */
public abstract class InOutCommand extends ACommand {
  /**
   * To get the suffix for a filename.
   *
   * @param pathName the pathname of the file
   * @return the suffix of the given pathname.
   * @throws IllegalStateException if the file does not have a suffix or the filename is invalid.
   */
  protected String getValidSuffix(String pathName) throws IllegalStateException {
    String fileName = new File(pathName).getName();
    String[] splitList = fileName.split("\\.");
    if (!fileName.contains(".") || fileName.endsWith(".")) {
      throw new IllegalStateException("Please specify the file format followed by '.'!");
    } else if (fileName.startsWith(".") && splitList.length < 3) {
      throw new IllegalStateException("Invalid File Name!");
    }
    return splitList[splitList.length - 1];
  }
}
