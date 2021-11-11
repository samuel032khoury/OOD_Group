package controller.command.visual;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import model.library.ImageLibModel;
import model.operation.visual.FlipOperation;
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
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " flipped image";
    super.perform(util, new FlipOperation(verticalFlip), model, commandQueue, view, descriptionOfEdit);
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
