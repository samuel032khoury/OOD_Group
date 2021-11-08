package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to get the size of (image loaded to) the library.
 */
public class SizeCommand<T extends ImageFile<T>, K extends ImageLibModel<T>> extends ACommand<T,K> {

  /**
   * Try to get the size of (image loaded to) the library.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there is extra argument followed.
   */
  @Override
  public void execute(K model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    expectNoMoreArgs(commandQueue);
    view.renderMessage("There are " + model.getLibSize() + " images in the library!");
  }

  /**
   * To have current performing command {@code size} as a String.
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return "Size";
  }
}
