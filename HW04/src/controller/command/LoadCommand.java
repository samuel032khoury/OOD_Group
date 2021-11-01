package controller.command;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class LoadCommand extends InOutCommand {
  @Override
  public void execute(ImageLibModel model, Scanner scanner,  IView view)
          throws IllegalStateException {
    try {
      String pathName = scanner.next();
      String fileName = scanner.next();
      ILoader loader = new LoadManager().provide(getValidSuffix(pathName));
      try {
        ImageFile img = loader.load(pathName);
        model.load(fileName, img);
      } catch (FileNotFoundException e) {
        view.renderError("Unable to find file " + pathName +"! Please check the fileName or the " +
                "filepath is correct!");
      }
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient inputs to run!");
    }
  }
}
