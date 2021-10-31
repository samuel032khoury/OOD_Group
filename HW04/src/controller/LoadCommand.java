package controller;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.library.ImageLibModel;
import model.imageFile.ImageFile;

public class LoadCommand implements ICommand {
  @Override
  public void execute(ImageLibModel model, Scanner scanner) throws FileNotFoundException {
    String pathname = scanner.next();
    String filename = scanner.next();
    String[] splitList = pathname.split("\\.");
    String suffix = splitList[splitList.length - 1];

    ILoader loader = new LoadManager().provideLoader(suffix);
    ImageFile img = loader.load(pathname);
    model.load(filename, img);

  }
}
