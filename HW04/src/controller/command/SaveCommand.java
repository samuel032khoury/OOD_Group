package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.utils.IWriter;
import controller.utils.WriteManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class SaveCommand implements ICommand{

  @Override
  public void execute(ImageLibModel model, Scanner scanner, IView view)
          throws NoSuchElementException {
    String pathName = scanner.next();
    String filename = scanner.next();
    ImageFile img = model.get(filename);

    String[] splitList = pathName.split("\\.");
    String suffix = splitList[splitList.length - 1];
    IWriter writer = new WriteManager().provide(suffix);
    writer.write(img, pathName);
  }
}
