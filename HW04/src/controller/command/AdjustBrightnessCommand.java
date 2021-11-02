package controller.command;

import java.util.Queue;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class AdjustBrightnessCommand extends ACommand {
  // true when try to brighten an image, false when try to darken an image.
  private final boolean brighten;
  private final String adjustment;

  public AdjustBrightnessCommand(boolean brighten) {
    this.brighten = brighten;
    this.adjustment = brighten ? "Brightened" : "Darkened";
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      int value = Integer.parseInt(getValidArgs(currCommand, this.adjustment.toLowerCase()));
      String imageName = super.getValidArgs(currCommand, this.adjustment.toLowerCase());
      String newImageName = super.getValidArgs(currCommand, this.adjustment.toLowerCase());
      this.expectNoMoreArgs(currCommand, this.adjustment.toLowerCase());
      String connection = (model.peek(newImageName) == null) ? " is named " : " has overwritten ";
      ImageFile imageFile = model.get(imageName);
      ImageFile newImageFile;
      if (brighten) {
        newImageFile = imageFile.brighten(value);
      } else {
        newImageFile = imageFile.darken(value);
      }
      model.loadImage(newImageName, newImageFile);
      view.renderMessage(this.adjustment + " image (value: " + value + ") of " + imageName +
              " has been created and" + connection + newImageName + ".");
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Expect an integer as the value for brightness adjustment, " +
              "but input is a string, try again!");
    }
  }
}
