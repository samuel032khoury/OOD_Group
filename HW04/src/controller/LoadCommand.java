package controller;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.library.ImageLibModel;

public class LoadCommand implements ICommand {
  @Override
  public void execute(ImageLibModel model, Scanner scanner) throws FileNotFoundException {
    String pathname = scanner.next();
    String filename = scanner.next();
    Color[][] contents = ImageUtil.readPPMIMG(pathname);

  }
}
