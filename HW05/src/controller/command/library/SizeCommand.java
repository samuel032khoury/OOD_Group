package controller.command.library;

import java.util.Queue;

import controller.command.macro.CommandUtil;
import controller.command.macro.ICommand;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to get the size of (image loaded to) the library.
 */
public class SizeCommand implements ICommand {

  /**
   * Try to get the size of (image loaded to) the library.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there is extra argument followed.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    CommandUtil util = new CommandUtil("Size");
    util.expectNoMoreArgs(commandQueue);
    view.renderMessage("There are " + model.getLibSize() + " images in the library!");
  }
}
