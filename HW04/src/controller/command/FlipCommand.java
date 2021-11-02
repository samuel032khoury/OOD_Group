package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class FlipCommand extends ACommand {
  // true when performing a vertical flip, false when performing a horizontal one.
  final boolean verticalFlip;

  /**
   * To construct a model of flipCommand.
   * @param verticalFlip true when performing a vertical flip,
   *                     false when performing a horizontal one.
   */
  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  /**
   * To flip a picture in the model.
   * @param model the model to mutate.
   * @param currCommand a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the command is too much or too few.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String imageName = super.getValidArgs(currCommand);
      String newImageName = super.getValidArgs(currCommand);
      String connection = (model.peek(newImageName) == null)? " is named " : " has overwritten ";
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
              + connection + newImageName + ".");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
