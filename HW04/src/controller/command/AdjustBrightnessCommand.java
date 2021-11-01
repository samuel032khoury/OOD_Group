package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class AdjustBrightnessCommand implements ICommand {
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
      String fileName = currCommand.remove();
      String newFileName = currCommand.remove();
      ImageFile imageFile = model.get(fileName);
      ImageFile newImageFile;
      if (brighten) {
        newImageFile = imageFile.brighten(value);
      } else {
        newImageFile = imageFile.darken(value);
      }
      model.loadImage(newFileName, newImageFile);
      String adjustment = brighten? "Brightened" : "Darkened";
      view.renderMessage(adjustment + " image (value: " + value + ") of " + fileName + " has been"
              + " created and is named " + newFileName + ".");
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Expect an integer but input is a string, try again!");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!!");
    }
  }
}
