package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import controller.utils.IWriter;
import controller.utils.WriteManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class SaveCommand extends InOutCommand{

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String pathName = currCommand.remove();
      String fileName = currCommand.remove();
      ImageFile img = model.get(fileName);
      IWriter writer = new WriteManager().provide(getValidSuffix(pathName));
      writer.write(img, pathName);
      view.renderMessage("Image " + fileName + " has been exported to " + pathName + ".");
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
