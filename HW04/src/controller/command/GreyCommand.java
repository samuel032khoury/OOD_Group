package controller.command;

import java.util.Queue;
import java.util.NoSuchElementException;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import model.operation.IChannelOperator;
import view.IImageProcessView;

public class GreyCommand extends ACommand {
  private final IChannelOperator channel;

  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    String componentName = channel.toString();
    String imageName = super.getValidArgs(currCommand, componentName.toLowerCase());
    String newImageName = super.getValidArgs(currCommand, componentName.toLowerCase());
    this.expectNoMoreArgs(currCommand, componentName.toLowerCase());
    String connection = (model.peek(newImageName) == null) ? " is named " : " has overwritten ";
    ImageFile imageFile = model.get(imageName);
    ImageFile newImageFile = imageFile.greyscale(this.channel);
    model.loadImage(newImageName, newImageFile);
    view.renderMessage(componentName + "-component image of " + imageName + " has been "
            + "created and" + connection + newImageName + ".");
  }
}
