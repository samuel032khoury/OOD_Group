package controller.command;

import java.util.Queue;

import controller.utils.QuitExecution;
import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to quit the program.
 */
public class QuitCommand<T extends ImageFile<T>, K extends ImageLibModel<T>> implements ICommand<T,K> {

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
  public void execute(K model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException, QuitExecution {
    if (commandQueue.peek() != null) {
      throw new IllegalStateException(
              "QUIT should be the last argument, try again!");
    }
    view.renderMessage("The program is quit!");
    throw new QuitExecution();
  }
}