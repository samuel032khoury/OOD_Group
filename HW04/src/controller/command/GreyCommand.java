package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import model.operation.IChannelOperator;
import view.IImageProcessView;

/**
 * A command to greyscale an image by unifying all channels with a value derived by performing the
 * given {@link IChannelOperator}.
 */
public class GreyCommand extends ACommand {
  private final IChannelOperator channel;

  /**
   * To construct a GreyCommand.
   *
   * @param channel select which channel the GreyCommand will use to change to grayscale.
   */
  public GreyCommand(IChannelOperator channel) {
    this.channel = channel;
  }

  /**
   * To produce a grayscale image in a model using the given {@link IChannelOperator}.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments, or the provided {@link
   *                               IChannelOperator} is unsupported.
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

  /**
   * To have current performing {@code greyscale} command as a String.
   *
   * @return a string indicating command that being performed
   */
  protected String currCommand() {
    return channel.toString();
  }
}
