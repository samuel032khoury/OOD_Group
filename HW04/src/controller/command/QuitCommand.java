package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

public class QuitCommand implements ICommand {

  /**
   * Modifies the model with the input from the user.
   *
   * @param model        the model to mutate.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    if(!(commandQueue.peek() == null)) {throw new IllegalStateException(
            "QUIT should be the last argument, try again!");}
    view.renderMessage("Quitting, please wait!");
    System.exit(0);
  }
}