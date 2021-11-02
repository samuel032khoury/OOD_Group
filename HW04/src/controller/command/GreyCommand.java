package controller.command;

import java.util.Queue;

import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import model.operation.IChannelOperator;
import view.IImageProcessView;

public class GreyCommand extends ACommand {
  private final IChannelOperator channel;

  /**
   * To construct a GreyCommand.
   * @param channel select which channel the GreyCommand will use to change to grayscale.
   */
  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  /**
   * To produce a gray scale image in a model using given channel.
   * @param model the model to mutate.
   * @param currCommand a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the argument is too much or too few,
   *         or the image channel is not compatible with the image.
   */
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
