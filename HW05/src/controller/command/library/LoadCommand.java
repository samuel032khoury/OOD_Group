package controller.command.library;

import java.util.Queue;

import controller.command.macro.CommandUtil;
import controller.utils.ILoader;
import controller.utils.LoadSuffixManager;
import model.imagefile.ImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to load an image from the user's machine to the program's library .
 */
public class LoadCommand extends InOutCommand {

  private final LoadSuffixManager loaderProvider;

  /**
   * Construct a LoadCommand that loads the images to the library.
   *
   * @param loaderProvider the {@link LoadSuffixManager} that can provide an appropriate loader
   *                       that transform an image from machine to a 2-D array based on the
   *                       file extension.
   */
  public LoadCommand(LoadSuffixManager loaderProvider) {
    this.loaderProvider = loaderProvider;
  }

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
    CommandUtil util = new CommandUtil(currCommand());
    String pathName = util.getValidArgs(commandQueue);
    String imageName = util.getValidArgs(commandQueue);
    util.expectNoMoreArgs(commandQueue);
    ILoader loader = loaderProvider.provide(getValidSuffix(pathName));
    String connection = util.getConnection(model.peek(imageName));
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
    return "Load";
  }
}
