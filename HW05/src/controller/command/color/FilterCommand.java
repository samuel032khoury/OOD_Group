package controller.command.color;

import java.util.Queue;

import controller.command.library.InOutCommand;
import controller.command.macro.CommandUtil;
import model.library.ImageLibModel;
import model.operation.color.FilterOperation;
import model.operation.opertor.filter.IFilterOperator;
import view.IImageProcessView;

public class FilterCommand extends InOutCommand {

  private final IFilterOperator filter;

  public FilterCommand(IFilterOperator filter) {
    this.filter = filter;
  }


  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view) throws IllegalStateException {
    CommandUtil util = new CommandUtil(currCommand());
    String descriptionOfEdit = currCommand() + " image";
    super.perform(util, new FilterOperation(this.filter),model,commandQueue,view, descriptionOfEdit);
  }

  @Override
  protected String currCommand() {
    return this.filter.toString();
  }
}
