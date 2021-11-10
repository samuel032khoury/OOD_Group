package controller.command.library;

import java.util.Queue;

import controller.command.macro.CommandUtil;
import controller.utils.IWriter;
import controller.utils.WriteSuffixManager;
import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

/**
 * A command to save an image from the program's library to the user's machine.
 */
public class SaveCommand extends InOutCommand {
  private final WriteSuffixManager writerProvider;

  public SaveCommand(WriteSuffixManager writerProvider) {
    this.writerProvider = writerProvider;
  }

  /**
   * Try to export a picture into a file.
   *
   * @param model        the image library.
   * @param commandQueue a queue of current unprocessed commands as strings.
   * @param view         the view to send output to.
   * @throws IllegalStateException if there are extra/insufficient arguments, if the view fails to
   *                               render message, if the image cannot be found in the library, or
   *                               the save process failed.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> commandQueue, IImageProcessView view)
          throws IllegalStateException {
    CommandUtil util = new CommandUtil(currCommand());
    String pathName = util.getValidArgs(commandQueue);
    String imageName = util.getValidArgs(commandQueue);
    util.expectNoMoreArgs(commandQueue);
    ReadOnlyImageFile img = model.peek(imageName);
    if (img == null) {
      throw new IllegalStateException(
              "Unable to save because image " + imageName + " cannot be found!");
    }
    IWriter writer = writerProvider.provide(getValidSuffix(pathName));
    writer.write(img, pathName);
    view.renderMessage("Image " + imageName + " has been exported to " + pathName + ".");
  }

  /**
   * To have current performing command {@code save} as a String.
   *
   * @return a string indicating command that being performed
   */
  @Override
  protected String currCommand() {
    return "Save";
  }
}
