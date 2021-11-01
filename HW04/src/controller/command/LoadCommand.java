package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

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
        ImageFile img = loader.loadFile(pathName);
        model.loadImage(fileName, img);
        view.renderMessage("Image file found at " + pathName + " has been imported and"
                + " is named " + fileName + ".");
      } catch (IllegalStateException e) {
        view.renderError(e.getMessage());
      }
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
