package controller.command.color;

import java.util.Queue;

import controller.command.macro.ACommand;
import controller.command.macro.CommandUtil;
import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import model.operation.color.FilterOperation;
import model.operation.opertor.filter.IFilterOperator;
import view.IImageProcessView;

/**
 * A command that applies a filter to an image by performing a kernel provided by the given {@link
 * IFilterOperator}.
 */
public class FilterCommand extends ACommand {

  private final IFilterOperator filter;

  /**
   * To construct a FilterCommand.
   *
   * @param filter A {@link IFilterOperator}, expected by {@link #execute} for getting a filtering
   *               rule (kernel) to be applied to the targeting {@link ImageFile}.
   */
  public FilterCommand(IFilterOperator filter) {
    this.filter = filter;
  }

  /**
   * To produce (and put) a filtered version of image stored in the {@link ImageLibModel} model
   * using the given {@link IFilterOperator}.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current (unprocessed) commands as strings.
   * @param view         the view where the output is sent to.
   * @throws IllegalStateException if there are extra/insufficient arguments, or the provided {@link
   *                               IFilterOperator} is unsupported.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " image";
    super.perform(util, new FilterOperation(this.filter), model, commandQueue,
            view, descriptionOfEdit);
  }

  /**
   * To have current performing {@code filter} command as a String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return this.filter.toString();
  }
}
