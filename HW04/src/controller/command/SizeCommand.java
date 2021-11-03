package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

public class SizeCommand extends ACommand {
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    expectNoMoreArgs(commandQueue);
    view.renderMessage("There are " + model.getLibSize() + " images in the library!");
  }

  @Override
  protected String currCommand() {
    return "size";
  }
}
