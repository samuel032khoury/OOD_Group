package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class AdjustBrightnessCommand implements ICommand{
  // true when try to brighten an image, false when try to darken an image.
  final boolean brighten;

  public AdjustBrightnessCommand(boolean brighten) {
    this.brighten = brighten;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try{
      int value = Integer.parseInt(currCommand.remove());
      String filename = currCommand.remove();
      String newFilename = currCommand.remove();
      ImageFile file = model.get(filename);
      ImageFile newFile;
      if(brighten) {
        newFile = file.brighten(value);
      } else {
        newFile = file.darken(value);
      }
      model.load(newFilename, newFile);
    } catch(NumberFormatException e) {
      throw new IllegalStateException("Expect an integer but input is a string");
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!!");
    }
  }
}
