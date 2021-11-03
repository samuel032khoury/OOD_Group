package controller.command;

import java.util.Queue;

import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * An abstract class for command supported by the image process program.
 */
public abstract class ACommand implements ICommand {

  /**
   * execute a particular operation specified by the {@code commandQueue} with the access to the
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

  /**
   * To have current performing {@link ICommand} (or subcommand if supported)as a String.
   *
   * @return a string indicating command that being performed
   */
  protected abstract String currCommand();

  protected String getConnection(ReadOnlyImageFile newImageName) {
    return newImageName == null ? " is named " : " has overwritten ";
  }


  // to check if arguments passed to command is a splitter & instead, which implies command may
  // have fewer arguments than needed and thus throw an IllegalStateException.
  protected String getValidArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Insufficient argument for " + currCommand().toLowerCase()
              + ", try again!");
    }
    return commandQueue.remove();
  }

  protected void expectNoMoreArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (!this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Too much arguments provided for "
              + currCommand().toLowerCase() + ", try again!");
    }
  }

  private boolean splitterOrNull(Queue<String> commandQueue) {
    return commandQueue.peek() == null || commandQueue.peek().equals("&");
  }
}