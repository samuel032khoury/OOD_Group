package controller.command.visual;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import model.library.ImageLibModel;
import model.operation.visual.BrightnessOperation;
import view.IImageProcessView;

/**
 * A command to adjust brightness of an image. With a boolean indicating the adjustment direction.
 */
public class AdjustBrightnessCommand extends ACommand {
  // true when try to brighten an image, false when try to darken an image.
  private final boolean brighten;

  /**
   * Setting the function the command in creating objects.
   *
   * @param brighten ture if try to brighten an image, false when try to darken an image.
   */
  public AdjustBrightnessCommand(boolean brighten) {
    this.brighten = brighten;
  }

  /**
   * To adjust the brightness of a selected image in the model, according to the {@code brighten}
   * value.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments, or the value to change
   *                               is not an integer.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    try {
      CommandUtil util = new CommandUtil(brighten ? "Brighten" : "Darken");
      int value = Integer.parseInt(util.getValidArgs(commandQueue));
      String descriptionOfEdit = currCommand() + " image (value: " + value + ")";
      super.perform(util, new BrightnessOperation(brighten, value), model, commandQueue, view,
              descriptionOfEdit);
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Expect an integer as the value for brightness adjustment, "
              + "but input is a string, try again!");
    }
  }

  /**
   * To have current performing {@code adjustment} command as a String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return brighten ? "Brightened" : "Darkened";
  }

}
