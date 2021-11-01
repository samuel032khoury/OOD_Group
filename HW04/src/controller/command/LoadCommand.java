package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class LoadCommand extends InOutCommand {
  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String pathName = currCommand.remove();
      String fileName = currCommand.remove();
      ILoader loader = new LoadManager().provide(getValidSuffix(pathName));
      try {
        ImageFile img = loader.load(pathName);
        model.load(fileName, img);
      } catch (FileNotFoundException e) {
        view.renderError("Unable to find file " + pathName +"! Please check the fileName or the " +
                "filepath is correct!");
      }
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
