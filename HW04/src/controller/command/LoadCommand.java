package controller.command;

import java.util.Queue;

import controller.utils.ILoader;
import controller.utils.LoadSuffixManager;
import model.imageFile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to load an image from the user's machine to the program's library .
 */
public class LoadCommand extends InOutCommand {
  /**
   * Try to load an image into the library.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments, suffix is unsupported,
   *                               or the provided file does not exist in the machine's file
   *                               system.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    String pathName = super.getValidArgs(commandQueue);
    String imageName = super.getValidArgs(commandQueue);
    super.expectNoMoreArgs(commandQueue);
    ILoader loader = new LoadSuffixManager().provide(getValidSuffix(pathName));
    String connection = super.getConnection(model.peek(imageName));
    ImageFile img = loader.loadFile(pathName);
    model.loadImage(imageName, img);
    view.renderMessage("Image file found at " + pathName + " has been imported and"
            + connection + imageName + ".");
  }

  /**
   * To have current performing command {@code load} as a String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return "load";
  }
}
