package controller.command;

import java.util.Queue;

import model.imagefile.ImageFile;
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
