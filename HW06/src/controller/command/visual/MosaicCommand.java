package controller.command.visual;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import controller.command.macro.ICommand;
import model.library.ImageLibModel;
import model.operation.visual.FlipOperation;
import model.operation.visual.MosaicOperation;
import view.text.IImageProcessView;

public class MosaicCommand extends ACommand {


  /**
   * execute a particular operation specified by the {@code commandQueue}, with the access to the
   * model and view.
   *
   * @param model        the image library controller dealing with
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view where the output is sent to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view) throws IllegalStateException {
    try {
      CommandUtil util = new CommandUtil(currCommand());
      int seed = Integer.parseInt(util.getValidArgs(commandQueue));
      String descriptionOfEdit = currCommand() + "ed image";
      super.perform(util, new MosaicOperation(seed), model,
              commandQueue, view, descriptionOfEdit);
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Expect an integer as the value for mosaic seed, "
              + "while input is not a pure integer, try again!");
    }
  }

  /**
   * To have current performing {@link ICommand} (or more detailed sub-command if supported)as a
   * String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return "Mosaic";
  }
}
