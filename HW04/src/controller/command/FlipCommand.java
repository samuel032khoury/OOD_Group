package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class FlipCommand implements ICommand {
  // true when performing a vertical flip, false when performing a horizontal one.
  final boolean verticalFlip;

  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try{
      String filename = currCommand.remove();
      String fileNewName = currCommand.remove();
      ImageFile file = model.get(filename);
      ImageFile transformerFile;
      if (verticalFlip) {
        transformerFile = file.vertiFlip();
      } else {
        transformerFile = file.horizFlip();
      }
      model.load(fileNewName, transformerFile);
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
