package controller.command;

import java.util.Queue;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class LoadCommand extends InOutCommand {
  /**
   * Load an image into the library
   * @param model the model to mutate.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the argument is too much or too little.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    String pathName = super.getValidArgs(commandQueue);
    String imageName = super.getValidArgs(commandQueue);
    super.expectNoMoreArgs(commandQueue);
      ILoader loader = new LoadManager().provide(getValidSuffix(pathName));
      String connection = super.getConnection(model.peek(imageName));
      ImageFile img = loader.loadFile(pathName);
      model.loadImage(imageName, img);
      view.renderMessage("Image file found at " + pathName + " has been imported and"
              + connection + imageName + ".");
  }

  @Override
  protected String currCommand() {
    return "load";
  }
}
