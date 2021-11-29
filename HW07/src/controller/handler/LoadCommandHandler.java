package controller.handler;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;

/**
 * Handles the "load" command. This command attempts to load an image from the specified destination
 * and caches it in the application state under a defined name, to be later used by other
 * operations.
 *
 * <p>Note that this is a command handler; this handler will not actually load an image into the
 * memory, but rather parse and validate the specific command and delegate the actual loading
 * process to the get image function.
 */
public class LoadCommandHandler implements ImageProcessCommandHandler {

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
    Image img = getImage.apply(imagePath);
    addImage.accept(imageName, img);
  }

  @Override
  public String getCommandName() {
    return "load";
  }

  @Override
  public String getHelpText() {
    return "load [image-path] [image-name]";
  }
}
