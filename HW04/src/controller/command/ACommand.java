package controller.command;

import java.util.Queue;

import model.imageFile.ReadOnlyImageFile;
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

  /**
   * To have current performing {@link ICommand} (or more detailed sub-command if supported)as a
   * String.
   *
   * @return a string indicating command that being performed
   */
  protected abstract String currCommand();

  /**
   * get the connection word for prompt, which indicates whether the given image is newly added
   * into the library or is overwriting existing images.
   *
   * @param newImageName the {@link ReadOnlyImageFile} inspected in the library.
   * @return "is named" when the imageFile is newly added, or "has overwritten" if the imageFile has
   *          been loaded before
   */
  protected String getConnection(ReadOnlyImageFile newImageName) {
    return newImageName == null ? " is named " : " has overwritten ";
  }


  /**
   * to check if arguments passed to command is a splitter "&" instead, which implies command may
   * have fewer arguments than needed and thus throw an IllegalStateException.
   *
   * @param commandQueue a queue of String contains unprocessed commands
   * @return a String if the head entry of the queue is a valid input
   * @throws IllegalStateException if the head entry of the queue is invalid (null or splitter "&")
   */
  protected String getValidArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Insufficient argument for " + currCommand().toLowerCase()
              + ", try again!");
    }
    return commandQueue.remove();
  }

  /**
   * Used to determine if the queue is empty or the arguments for the current command has been
   * exhausted.
   *
   * @param commandQueue a queue of String contains unprocessed commands
   * @throws IllegalStateException if there's extra arguments follows when there shouldn't be any.
   */
  protected void expectNoMoreArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (!this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Too much arguments provided for "
              + currCommand().toLowerCase() + ", try again!");
    }
  }

  /**
   * To check if the command queue is empty or the head element of the queue is the splitter "&".
   *
   * @param commandQueue a queue of String contains unprocessed commands
   * @return true when the queue is empty or the head element of the queue is the splitter "&".
   */
  private boolean splitterOrNull(Queue<String> commandQueue) {
    return commandQueue.peek() == null || commandQueue.peek().equals("&");
  }
}