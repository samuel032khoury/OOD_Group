package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import model.operation.IChannelOperator;
import view.IImageProcessView;

public class GreyCommand implements ICommand {
  private final IChannelOperator channel;

  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    try {
      String imageName = currCommand.remove();
      String newImageName = currCommand.remove();
      ImageFile imageFile = model.get(imageName);
      ImageFile newImageFile = imageFile.greyscale(this.channel);
      model.loadImage(newImageName, newImageFile);
      view.renderMessage(channel.toString() + "-component image of " + imageName + " has been "
              + "created and is named " + imageName + ".");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
