package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class FlipCommand implements ICommand {
  // true when performing a vertical flip, false when performing a horizontal one.
  final boolean verticalFlip;

  public FlipCommand(boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  @Override
  public void execute(ImageLibModel model, Scanner scanner, IView view)
          throws NoSuchElementException {
    String filename = scanner.next();
    String fileNewName = scanner.next();
    ImageFile file = model.get(filename);
    ImageFile transformerFile;
    if (verticalFlip) {
      transformerFile = file.vertiFlip();
    } else {
      transformerFile = file.horizFlip();
    }
    model.load(fileNewName, transformerFile);
  }
}
