package controller.command.macro;

import java.util.Queue;

import controller.utils.QuitExecution;
import model.library.ImageLibModel;
import view.text.IImageProcessView;

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
   * @throws QuitExecution         when a {@link QuitCommand} is executing.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException, QuitExecution {
    if (commandQueue.peek() != null) {
      throw new IllegalStateException(
              "QUIT should be the last argument, try again!");
    }
    view.renderMessage("The program is quit!");
    throw new QuitExecution();
  }
}