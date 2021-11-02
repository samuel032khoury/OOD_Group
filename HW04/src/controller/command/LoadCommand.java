package controller.command;

import java.util.Queue;

import controller.utils.ILoader;
import controller.utils.LoadManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class LoadCommand extends InOutCommand {
  /**
   * Load a image into the library
   * @param model the model to mutate.
   * @param currCommand a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if the argument is too much or too little.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    String pathName = super.getValidArgs(currCommand, "load");
    String imageName = super.getValidArgs(currCommand, "load");
    this.expectNoMoreArgs(currCommand, "load");
      ILoader loader = new LoadManager().provide(getValidSuffix(pathName));
      String connection = (model.peek(imageName) == null) ? " is named " : " has overwritten ";
      ImageFile img = loader.loadFile(pathName);
      model.loadImage(imageName, img);
      view.renderMessage("Image file found at " + pathName + " has been imported and"
              + connection + imageName + ".");
  }
}
