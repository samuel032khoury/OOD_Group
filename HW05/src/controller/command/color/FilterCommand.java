package controller.command.color;

import java.util.Queue;

import controller.command.library.InOutCommand;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class FilterCommand extends InOutCommand {


  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view) throws IllegalStateException {

  }

  @Override
  protected String currCommand() {
    return null;
  }
}
