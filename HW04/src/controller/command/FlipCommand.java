package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class FlipCommand extends ACommand {
  // true when performing a vertical flip, false when performing a horizontal one.
  private final boolean verticalFlip;
  private final String flipDirection;

  /**
   * To construct a model of flipCommand.
   * @param verticalFlip true when performing a vertical flip,
   *                     false when performing a horizontal one.
   */
  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
    this.flipDirection = verticalFlip ? "Vertical" : "Horizontal";
  }

  /**
   * To flip a picture in the model.
   * @param model the model to mutate.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the command is too much or too few.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    String imageName = super.getValidArgs(commandQueue);
    String newImageName = super.getValidArgs(commandQueue);
    super.expectNoMoreArgs(commandQueue);
    String connection = super.getConnection(model.peek(newImageName));
    ImageFile imageFile = model.get(imageName);
    ImageFile newImageFile;
    if (verticalFlip) {
      newImageFile = imageFile.vertiFlip();
    } else {
      newImageFile = imageFile.horizFlip();
    }
    model.loadImage(newImageName, newImageFile);
    view.renderMessage(currCommand() + " flipped image of " + imageName + " has been "
            + "created and" + connection + newImageName + ".");
  }

  @Override
  protected String currCommand() {
    return this.flipDirection;
  }
}
