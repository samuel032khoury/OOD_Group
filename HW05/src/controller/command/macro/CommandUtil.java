package controller.command.macro;

import java.util.Queue;

import model.imagefile.ReadOnlyImageFile;

public class CommandUtil {
  private final String currCommand;

  /**
   * To construct a {@link CommandUtil} that helps parse command arguments with the information of
   * the current running command.
   *
   * @param currCommand current executing command
   */
  public CommandUtil(String currCommand) {
    this.currCommand = currCommand;
  }

  /**
   * get the connection word for prompt, based on the pre-existence of the given {@link
   * ReadOnlyImageFile}.
   *
   * @param newImageName the {@link ReadOnlyImageFile} inspected in the library.
   * @return "is named" if the given {@link ReadOnlyImageFile} is newly created, or "has
   * overwritten" if the {@link ReadOnlyImageFile} is pre-existed
   */
  public String getConnection(ReadOnlyImageFile newImageName) {
    return newImageName == null ? " is named " : " has overwritten ";
  }


  /**
   * to get a valid arguments (non-null/splitter) if it's possible.
   *
   * @param commandQueue a queue of String contains unprocessed commands
   * @return a String if the head entry of the queue is a valid input
   * @throws IllegalStateException if the head entry of the queue is invalid (null or splitter "&")
   *                               whenever it should have at least one more valid arguments
   */
  public String getValidArgs(Queue<String> commandQueue)
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
  public void expectNoMoreArgs(Queue<String> commandQueue)
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
