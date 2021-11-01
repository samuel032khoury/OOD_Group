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
      String fileName = currCommand.remove();
      String newFileName = currCommand.remove();
      ImageFile imageFile = model.get(fileName);
      ImageFile newImageFile;
      if (verticalFlip) {
        newImageFile = imageFile.vertiFlip();
      } else {
        newImageFile = imageFile.horizFlip();
      }
      model.loadImage(newFileName, newImageFile);
      String flipDirection = verticalFlip? "Vertical" : "Horizontal";
      view.renderMessage(flipDirection + " flipped image of " + fileName + " has been created and"
              + " is named " + newFileName + ".");
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
