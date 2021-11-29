package controller.handler;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;

/**
 * Handles the "save" command. The "save" command takes a cached file from the application state
 * memory and attempts to save (export) it into a specified file.
 *
 * <p>Note that this is merely a command handler; the actual process of loading and saving an image
 * is delegated to the getImage and addImage object-functions.
 */
public class SaveCommandHandler implements ImageProcessCommandHandler {

  @Override
  public void process(List<String> args, Function<String, Image> getImage,
      BiConsumer<String, Image> addImage)
      throws IllegalArgumentException {
    if (args.size() != 2) {
      throw new IllegalArgumentException(
          String.format("Expected 2 arguments, received %d", args.size()));
    }

    String imagePath = args.get(0);
    String imageName = args.get(1);

    if (imagePath.isEmpty() || imageName.isEmpty()) {
      throw new IllegalArgumentException("Image names cannot be empty");
    }

    // get the image from the cached list, or load it from memory
    Image img = getImage.apply(imageName);
    addImage.accept(imagePath, img);
  }

  @Override
  public String getCommandName() {
    return "save";
  }

  @Override
  public String getHelpText() {
    return "save [image-path] [image-name]";
  }
}
