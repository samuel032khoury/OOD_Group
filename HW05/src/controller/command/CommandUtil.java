package controller.command;

import java.util.Queue;

import model.imagefile.ReadOnlyImageFile;

public class CommandUtil {
  private final String currCommand;

  public CommandUtil(String currCommand) {
    this.currCommand = currCommand;
  }

  /**
   * get the connection word for prompt, which indicates whether the given image is newly added into
   * the library or is overwriting existing images.
   *
   * @param newImageName the {@link ReadOnlyImageFile} inspected in the library.
   * @return "is named" when the imageFile is newly added, or "has overwritten" if the imageFile has
   * been loaded before
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
    if (splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Insufficient argument for " + currCommand.toLowerCase()
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
    if (!splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Too much arguments provided for "
              + currCommand.toLowerCase() + ", try again!");
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
