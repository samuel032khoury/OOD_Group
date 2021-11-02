package controller.command;

import java.util.Queue;

import controller.utils.IWriter;
import controller.utils.WriteManager;
import model.imageFile.ReadOnlyImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class SaveCommand extends InOutCommand {

  /**
   * Try to export a picture into a file.
   * @param model the model to mutate.
   * @param currCommand a queue of current unprocessed commands as strings.
   * @param view the view to send output to.
   * @throws IllegalStateException if there is too much or too less arguement, the view is unable to
   * render message. The image cannot be found.
   */
  @Override
  public void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException {
    String pathName = super.getValidArgs(currCommand, "save");
    String imageName = super.getValidArgs(currCommand, "save");
    this.expectNoMoreArgs(currCommand, "save");
    ReadOnlyImageFile img = model.peek(imageName);
      if (img == null) {
        throw new IllegalStateException(
                "Unable to save because image " + imageName + " cannot be found!");
      }
      IWriter writer = new WriteManager().provide(getValidSuffix(pathName));
      writer.write(img, pathName);
      view.renderMessage("Image " + imageName + " has been exported to " + pathName + ".");
  }
}
