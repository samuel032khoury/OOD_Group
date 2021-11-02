package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * An abstract class for a command, have some common functionality.
 */
public abstract class ACommand implements ICommand {
  /**
   * To execute of the abstract command.
   * @param model the model to mutate.
   * @param currCommand a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if there is too much or too little commands,
   *         the view is unable to render message, more will be derteimined by the implementation.
   */
  public abstract void execute(ImageLibModel model, Queue<String> currCommand,
                               IImageProcessView view) throws IllegalStateException;

  /**
   * Return the next valid command from the queue.
   * @param currCommand A queue of valid command.
   * @return the next valid command.
   * @throws IllegalStateException if the next command found is "&"
   *         (this means the current command is ended)
   */
  protected String getValidArgs(Queue<String> currCommand) {
    if (currCommand.peek() == null || currCommand.peek().equals("&")) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
    return currCommand.remove();
  }
}