package controller.command;

import java.io.File;

public abstract class InOutCommand extends ACommand {
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
