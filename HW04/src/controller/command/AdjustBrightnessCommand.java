package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class AdjustBrightnessCommand extends ACommand {
  // true when try to brighten an image, false when try to darken an image.
  final boolean brighten;

  public AdjustBrightnessCommand(boolean brighten) {
    this.brighten = brighten;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      int value = Integer.parseInt(currCommand.remove());
      String imageName = super.getValidArgs(currCommand);
      String newImageName = super.getValidArgs(currCommand);
      ImageFile imageFile = model.get(imageName);
      ImageFile newImageFile;
      if (brighten) {
        newImageFile = imageFile.brighten(value);
      } else {
        newImageFile = imageFile.darken(value);
      }
      model.loadImage(newImageName, newImageFile);
      String adjustment = brighten ? "Brightened" : "Darkened";
      view.renderMessage(adjustment + " image (value: " + value + ") of " + imageName + " has been"
              + " created and is named " + newImageName + ".");
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Expect an integer as the value for brightness adjustment, " +
              "but input is a string, try again!");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!1");
    }
  }
}
