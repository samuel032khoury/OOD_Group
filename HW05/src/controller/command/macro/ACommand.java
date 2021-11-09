package controller.command.macro;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.operation.IImageOperation;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * An abstract class for command supported by the image process program.
 */
public abstract class ACommand implements ICommand {

  /**
   * execute a particular operation specified by the {@code commandQueue}, with the access to the
   * model and view.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  @Override
  public abstract void execute(ImageLibModel model, Queue<String> commandQueue,
                               IImageProcessView view) throws IllegalStateException;

  protected final void perform(CommandUtil util, IImageOperation operation, ImageLibModel model,
                               Queue<String> commandQueue, IImageProcessView view,
                               String descriptionOfEdit) {
    String imageName = util.getValidArgs(commandQueue);
    String newImageName = util.getValidArgs(commandQueue);
    util.expectNoMoreArgs(commandQueue);
    String connection = util.getConnection(model.peek(newImageName));
    ImageFile imageFile = model.get(imageName);
    ImageFile newImageFile = imageFile.applyOperation(operation);
    model.loadImage(newImageName, newImageFile);
    view.renderMessage(descriptionOfEdit + " of " + imageName +
            " has been created and" + connection + newImageName + ".");
  }

  /**
   * To have current performing {@link ICommand} (or more detailed sub-command if supported)as a
   * String.
   *
   * @return a string indicating command that being performed
   */
  protected abstract String currCommand();
}