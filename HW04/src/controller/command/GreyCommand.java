package controller.command;

import java.util.Scanner;

import controller.command.ICommand;
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
  public void execute(ImageLibModel model, Scanner scanner, IView view) {
    String filename = scanner.next();
    String newFilename = scanner.next();
    ImageFile file = model.get(filename);
    ImageFile newFile = null;
    try {
      newFile = file.greyscale(this.channel);
    } catch (IllegalArgumentException e) {
      view.renderError(e.getMessage());
    }
    model.load(newFilename, newFile);
  }
}
