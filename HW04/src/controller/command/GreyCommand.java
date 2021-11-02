package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
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
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the argument is too much or too few,
   *         or the image channel is not compatible with the image.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    String imageName = super.getValidArgs(commandQueue);
    String newImageName = super.getValidArgs(commandQueue);
    super.expectNoMoreArgs(commandQueue);
    String connection = super.getConnection(model.peek(newImageName));
    ImageFile imageFile = model.get(imageName);
    ImageFile newImageFile = imageFile.greyscale(this.channel);
    model.loadImage(newImageName, newImageFile);
    view.renderMessage(currCommand() + "-component image of " + imageName + " has been "
            + "created and" + connection + newImageName + ".");
  }

  protected String currCommand() {
    return channel.toString();
  }
}
