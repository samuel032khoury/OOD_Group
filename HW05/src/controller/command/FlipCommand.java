package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.imageoperation.FlipOperation;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to flip an image. With a boolean indicating the flip direction.
 */
public class FlipCommand extends ACommand {
  // true when try to perform a vertical flip, false when a horizontal one.
  private final boolean verticalFlip;

  /**
   * To construct a model of flipCommand.
   *
   * @param verticalFlip true when performing a vertical flip, false when performing a horizontal
   *                     one.
   */
  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  /**
   * To flip an image selected in the model,  the flip direction is determined by {@code
   * verticalFlip}.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue,
                      IImageProcessView view)
          throws IllegalStateException {
    String imageName = super.getValidArgs(commandQueue);
    String newImageName = super.getValidArgs(commandQueue);
    super.expectNoMoreArgs(commandQueue);
    String connection = super.getConnection(model.peek(newImageName));
    ImageFile imageFile = model.get(imageName);
    ImageFile newImageFile = imageFile.applyOperation(new FlipOperation(verticalFlip));
    model.loadImage(newImageName, newImageFile);
    view.renderMessage(currCommand() + " flipped image of " + imageName + " has been "
            + "created and" + connection + newImageName + ".");
  }

  /**
   * To have current performing {@code flipping} command as a String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return verticalFlip ? "Vertical" : "Horizontal";
  }
}
