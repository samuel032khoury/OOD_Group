package controller.command.color;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import model.operation.color.GreyscaleOperation;
import model.library.ImageLibModel;
import model.operation.opertor.colortrans.IColorTransOperator;
import view.IImageProcessView;

/**
 * A command to greyscale an image by unifying all channels with a value derived by performing the
 * given {@link IColorTransOperator}.
 */
public class GreyCommand extends ACommand {
  private final IColorTransOperator operator;

  /**
   * To construct a GreyCommand.
   *
   * @param operator A {@link IColorTransOperator}, expected by {@link #execute} for getting a
   *                value to be applied to all channels
   */
  public GreyCommand(IColorTransOperator operator) {
    this.operator = operator;
  }

  /**
   * To produce a grayscale image in a model using the given {@link IColorTransOperator}.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments, or the provided {@link
   *                               IColorTransOperator} is unsupported.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " image";
    super.perform(util, new GreyscaleOperation(this.operator),model,commandQueue,view, descriptionOfEdit);
  }

  /**
   * To have current performing {@code greyscale} command as a String.
   *
   * @return a string indicating command that being performed
   */
  protected String currCommand() {
    return operator.toString() + "-component";
  }
}
