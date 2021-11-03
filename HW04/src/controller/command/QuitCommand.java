package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to quit the program.
 */
public class QuitCommand implements ICommand {

  /**
   * Try to quit the program.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if quit is not the last argument in a line.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    if (commandQueue.peek() != null) {
      throw new IllegalStateException(
              "QUIT should be the last argument, try again!");
    }
    view.renderMessage("The program is quit!");
    System.exit(0);
  }
}