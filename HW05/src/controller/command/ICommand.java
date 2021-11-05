package controller.command;

import java.util.Queue;

import controller.utils.QuitExecution;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * An interface for command, which is a function modifies the {@link model.imagefile.ImageFile}
 * model. Different command depends on the implementation for {@link #execute} in concrete classes.
 */
public interface ICommand {
  /**
   * execute a particular operation specified by the {@code commandQueue} with the access to the
   * model and view.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException determined by the specific implementation.
   * @throws QuitExecution when a {@link QuitCommand} is executing.
   */
  void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException, QuitExecution;
}
