package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * An interface for command, which is a function modifies the model.
 * Different command will have different representation of the execute.
 */
public interface ICommand {
  /**
   * Modifies the model with the input from the user.
   * @param model the model to mutate.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException;
}


