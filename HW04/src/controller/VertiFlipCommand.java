package controller;

import java.io.StringReader;
import java.util.Scanner;

import model.ImageFile;
import model.ImageLibModel;

public class VertiFlipCommand implements ICommand {

  @Override
  public void execute(ImageLibModel model, Scanner scanner) {
    String filename = scanner.next();
    String fileNewName = scanner.next();
    ImageFile file = model.get(filename);
    ImageFile transformerFile = file.vertiFlip();
    model.load(fileNewName, transformerFile);
  }
}
