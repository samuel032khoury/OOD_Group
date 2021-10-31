package controller.command;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.library.ImageLibModel;
import model.imageFile.ImageFile;
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
