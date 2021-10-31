package controller.command;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class BrightenCommand implements ICommand {

  @Override
  public void execute(ImageLibModel model, Scanner scanner, IView view)
          throws IllegalStateException {
    try{
      int value = scanner.nextInt();;
      String filename = scanner.next();;
      String newFilename = scanner.next();;
      ImageFile file = model.get(filename);
      ImageFile newFile = file.brighten(value);
      model.load(newFilename, newFile);
    } catch(InputMismatchException e) {
      throw new IllegalStateException("Expect an integer but input is a String");
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient inputs to run!");
    }
  }
}
