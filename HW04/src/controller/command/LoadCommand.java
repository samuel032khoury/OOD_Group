package controller.command;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class LoadCommand implements ICommand {
  @Override
  public void execute(ImageLibModel model, Scanner scanner,  IView view)
          throws IllegalStateException {
    try {
      String pathname = scanner.next();
      String filename = scanner.next();
      String[] splitList = pathname.split("\\.");
      String suffix = splitList[splitList.length - 1];
      ILoader loader = new LoadManager().provide(suffix);
      try {
        ImageFile img = loader.load(pathname);
        model.load(filename, img);
      } catch (FileNotFoundException e) {
        view.renderError("Unable to find file " + filename +"! Please check the filename or the " +
                "filepath is correct!");
      }
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient inputs to run!");
    }
  }
}
