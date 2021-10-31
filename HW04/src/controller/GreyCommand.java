package controller;

import java.util.Scanner;

import model.IChannelOperator;
import model.ImageFile;
import model.ImageLibModel;

public class GreyCommand implements ICommand{
  private final IChannelOperator channel;

  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  @Override
  public void execute(ImageLibModel model, Scanner scanner) {
    String filename = scanner.next();
    String newFilename = scanner.next();
    ImageFile file = model.get(filename);
    ImageFile newFile = file.greyscale(this.channel);
    model.load(newFilename, newFile);
  }
}
