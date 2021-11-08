package controller.command.color;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import controller.command.macro.ICommand;
import model.operation.color.TintingOperation;
import model.library.ImageLibModel;
import model.operation.opertor.colortrans.IColorTransOperator;
import view.IImageProcessView;

public class TintingCommand extends ACommand {
  private final IColorTransOperator operator;

  public TintingCommand(IColorTransOperator operator) {
    this.operator = operator;
  }

  /**
   * execute a particular operation specified by the {@code commandQueue}, with the access to the
   * model and view.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException determined by the specific implementation.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view) throws IllegalStateException {
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " image";
    super.perform(util,new TintingOperation(this.operator), model, commandQueue, view, descriptionOfEdit);
  }

  /**
   * To have current performing {@link ICommand} (or more detailed sub-command if supported)as a
   * String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return operator.toString();
  }
}
