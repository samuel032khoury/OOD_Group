package controller.command;

import java.io.File;
import java.util.Scanner;

import model.library.ImageLibModel;
import view.IView;

public abstract class InOutCommand implements ICommand {
  @Override
  public abstract void execute(ImageLibModel model, Scanner scanner, IView view)
          throws IllegalStateException;

  protected String getValidSuffix(String pathName) throws IllegalStateException{
    String fileName = new File(pathName).getName();
    String[] splitList = fileName.split("\\.");
    if(!fileName.contains(".") || fileName.endsWith(".")) {
      throw new IllegalStateException("Please specify a file format followed by '.'!");
    } else if(fileName.startsWith(".") && splitList.length < 3) {
      throw new IllegalStateException("Invalid File Name!");
    }
    return splitList[splitList.length - 1];
  }
}
