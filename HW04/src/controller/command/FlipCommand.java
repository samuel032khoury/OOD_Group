package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class FlipCommand extends ACommand{
  // true when performing a vertical flip, false when performing a horizontal one.
  final boolean verticalFlip;

  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String imageName = super.getValidArgs(currCommand);
      String newImageName = super.getValidArgs(currCommand);
      ImageFile imageFile = model.get(imageName);
      ImageFile newImageFile;
      if (verticalFlip) {
        newImageFile = imageFile.vertiFlip();
      } else {
        newImageFile = imageFile.horizFlip();
      }
      model.loadImage(newImageName, newImageFile);
      String flipDirection = verticalFlip ? "Vertical" : "Horizontal";
      view.renderMessage(flipDirection + " flipped image of " + imageName + " has been created and"
              + " is named " + newImageName + ".");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
