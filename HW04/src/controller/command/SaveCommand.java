package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import controller.utils.IWriter;
import controller.utils.WriteManager;
import model.imageFile.ReadOnlyImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class SaveCommand extends InOutCommand {

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String pathName = super.getValidArgs(currCommand);
      String imageName = super.getValidArgs(currCommand);
      ReadOnlyImageFile img = model.peek(imageName);
      if (img == null) {
        throw new IllegalStateException(
                "Unable to save because image " + imageName + " cannot be found!");
      }
      IWriter writer = new WriteManager().provide(getValidSuffix(pathName));
      writer.write(img, pathName);
      view.renderMessage("Image " + imageName + " has been exported to " + pathName + ".");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
