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
          throws IllegalStateException{
    try {
      String fileName = currCommand.remove();
      String newFileName = currCommand.remove();
      ImageFile imageFile = model.get(fileName);
      ImageFile newImageFile = imageFile.greyscale(this.channel);
      model.loadImage(newFileName, newImageFile);
      view.renderMessage(channel.toString() + "-component image of " + fileName + " has been "
              + "created and is named " + fileName + ".");
    } catch(NoSuchElementException e) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
  }
}
