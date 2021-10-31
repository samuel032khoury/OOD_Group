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
          throws NoSuchElementException {
    String pathname = scanner.next();
    String filename = scanner.next();
    String[] splitList = pathname.split("\\.");
    String suffix = splitList[splitList.length - 1];

    ILoader loader = new LoadManager().provide(suffix);
    ImageFile img = null;
    try {
      img = loader.load(pathname);
    } catch (FileNotFoundException e) {
      view.renderError(e.getMessage());
      return;
    }

    model.load(filename, img);
  }
}
