package controller;

import java.util.Scanner;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;

public class VertiFlipCommand implements ICommand {

  @Override
  public void execute(ImageLibModel model, Scanner scanner) {
    // while
    String filename = scanner.next();
    String fileNewName = scanner.next();
    ImageFile file = model.get(filename);
    ImageFile transformerFile = file.vertiFlip();
    model.load(fileNewName, transformerFile);
  }
}
