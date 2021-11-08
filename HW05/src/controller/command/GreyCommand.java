package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.imageoperation.ColorTransformOperation;
import model.imageoperation.GreyscaleOperation;
import model.library.ImageLibModel;
import model.operation.IChannelOperator;
import view.IImageProcessView;

/**
 * A command to greyscale an image by unifying all channels with a value derived by performing the
 * given {@link IChannelOperator}.
 */
public class GreyCommand extends ACommand {
  private final IChannelOperator operator;

  /**
   * To construct a GreyCommand.
   *
   * @param operator A {@link IChannelOperator}, expected by {@link #execute} for getting a
   *                value to be applied to all channels
   */
  public GreyCommand(IChannelOperator operator) {
    this.operator = operator;
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
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " image";
    super.perform(util, new GreyscaleOperation(this.operator),model,commandQueue,view, descriptionOfEdit);
//    String imageName = util.getValidArgs(commandQueue);
//    String newImageName = util.getValidArgs(commandQueue);
//    util.expectNoMoreArgs(commandQueue);
//    String connection = util.getConnection(model.peek(newImageName));
//    ImageFile imageFile = model.get(imageName);
//    ImageFile newImageFile = imageFile.applyOperation(new GreyscaleOperation(this.operator));
////    ImageFile newImageFile = imageFile.applyOperation(new ColorTransformOperation(this.operator));
//    model.loadImage(newImageName, newImageFile);
//    view.renderMessage(currCommand() + "-component image of " + imageName + " has been "
//            + "created and" + connection + newImageName + ".");
  }

  /**
   * To have current performing {@code greyscale} command as a String.
   *
   * @return a string indicating command that being performed
   */
  protected String currCommand() {
    return operator.toString() + "-component";
  }
}
