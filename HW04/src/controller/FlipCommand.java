package controller;

import java.io.StringReader;
import java.util.Scanner;

import model.ImageFile;
import model.ImageLibModel;

public class FlipCommand implements ICommand {
  // if false, that means a horizontal flip
  Boolean verticalFlip = null;

  public FlipCommand(Boolean verticalFlip) {
    this.verticalFlip = verticalFlip;
  }

  @Override
  public void execute(ImageLibModel model, Scanner scanner) {
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
