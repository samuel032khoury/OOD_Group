package controller.command;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.operation.IChannelOperator;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IView;

public class GreyCommand implements ICommand {
  private final IChannelOperator channel;

  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  @Override
  public void execute(ImageLibModel model, Scanner scanner, IView view)
          throws IllegalStateException{
    try {
      String filename = scanner.next();
      String newFilename = scanner.next();
      ImageFile file = model.get(filename);
      ImageFile newFile = file.greyscale(this.channel);
      model.load(newFilename, newFile);
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient inputs to run!");
    }
  }
}
