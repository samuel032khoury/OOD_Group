package controller.command.macro;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import model.operation.IImageOperation;
import view.IImageProcessView;

/**
 * An abstract class for command supported by the image process program.
 */
public abstract class ACommand implements ICommand {

  /**
   * execute a particular operation specified by the {@code commandQueue}, with the access to the
   * model and view.
   *
   * @param model        the image library controller dealing with
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view where the output is sent to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  @Override
  public abstract void execute(ImageLibModel model, Queue<String> commandQueue,
                               IImageProcessView view) throws IllegalStateException;

  /**
   * perform a standard procedure when dealing with most command execution, this method parse an
   * {@code imageName} to be searched in the {@link ImageLibModel} and a {@code newImageName} as the
   * new name for the newly generated {@link ImageFile}, from the given {@code CommandQueue}. It's
   * also interacts with {@link IImageProcessView} to render standard operation message.
   *
   * @param util              a {@link CommandUtil} object with information of the current
   *                          processing command
   * @param operation         a {@link IImageOperation} object directs what should be done regards
   *                          the {@link ImageFile} found in the {@link ImageLibModel}
   * @param model             the image library controller dealing with.
   * @param commandQueue      a queue of current unprocessed commands as strings.
   * @param view              the view where the output is sent to.
   * @param descriptionOfEdit a detailed description of the current operation, for feedback
   *                          purpose.
   */
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
    view.renderMessage(descriptionOfEdit + " of " + imageName
            + " has been created and" + connection + newImageName + ".");
  }

  /**
   * To have current performing {@link ICommand} (or more detailed sub-command if supported)as a
   * String.
   *
   * @return a string indicating command that being performed
   */
  protected abstract String currCommand();
}