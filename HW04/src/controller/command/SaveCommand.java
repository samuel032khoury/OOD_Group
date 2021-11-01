package controller.command;

import java.util.Scanner;
import java.util.NoSuchElementException;

import controller.utils.IWriter;
import controller.utils.WriteManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class SaveCommand extends InOutCommand{

  @Override
  public void execute(ImageLibModel model, Scanner scanner, IView view)
          throws IllegalStateException {
    try {
      String pathName = scanner.next();
      String fileName = scanner.next();
      ImageFile img = model.get(fileName);
      IWriter writer = new WriteManager().provide(getValidSuffix(pathName));
      writer.write(img, pathName);
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient inputs to run!");
    }
  }
}
