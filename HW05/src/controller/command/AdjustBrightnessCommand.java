package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to adjust brightness of an image. With a boolean indicating the adjustment direction.
 */
public class AdjustBrightnessCommand extends ACommand {
  // true when try to brighten an image, false when try to darken an image.
  private final boolean brighten;
  private final String adjustment;

  /**
   * Setting the function the command in creating objects.
   *
   * @param brighten ture if try to brighten an image, false when try to darken an image.
   */
  public AdjustBrightnessCommand(boolean brighten) {
    this.brighten = brighten;
    this.adjustment = brighten ? "Brightened" : "Darkened";
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
      int value = Integer.parseInt(super.getValidArgs(commandQueue));
      String imageName = super.getValidArgs(commandQueue);
      String newImageName = super.getValidArgs(commandQueue);
      super.expectNoMoreArgs(commandQueue);
      String connection = super.getConnection(model.peek(newImageName));
      ImageFile imageFile = model.get(imageName);
      ImageFile newImageFile;
      if (brighten) {
        newImageFile = imageFile.brighten(value);
      } else {
        newImageFile = imageFile.darken(value);
      }
      model.loadImage(newImageName, newImageFile);
      view.renderMessage(currCommand() + " image (value: " + value + ") of " + imageName
              + " has been created and" + connection + newImageName + ".");
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
    return this.adjustment;
  }

}
