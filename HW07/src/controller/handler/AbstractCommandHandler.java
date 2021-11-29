package controller.handler;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;
import model.image.operation.ImageOperation;

/**
 * A superclass for all the command handlers.
 *
 * <p>Note that, however, this class is a command handler, and thus it actually will not
 * directly perform the operations on the images. Instead, it will parse the appropriate command and
 * delegate the actual transformation to the appropriate instance of {@link ImageOperation}.
 */
public abstract class AbstractCommandHandler implements ImageProcessCommandHandler {

  /**
   * Gets the specific to the subclass operation that will be performed on the image. For example,
   * for {@link controller.handler.colortransform.linear.grayscale.BlueComponentCommandHandler},
   * that operation would be
   * {@link model.image.operation.colortransform.linear.grayscale.BlueGrayscaleOperation}.
   *
   * @return The image operation to be used
   */
  protected abstract ImageOperation getOperation();

  @Override
  public void process(List<String> args, Function<String, Image> getImage,
      BiConsumer<String, Image> addImage) throws IllegalArgumentException {
    if (args.size() != 2) {
      throw new IllegalArgumentException(
          String.format("Expected 2 arguments, received %d", args.size()));
    }

    String imageName = args.get(0);
    String targetImageName = args.get(args.size() - 1); // last argument

    if (imageName.isEmpty() || targetImageName.isEmpty()) {
      throw new IllegalArgumentException("Image names cannot be empty");
    }

    Image img = getImage.apply(imageName);
    addImage.accept(targetImageName, getOperation().apply(img));
  }

  @Override
  public String getHelpText() {
    return String.format("%s [image-name] [dest-image-name]", getCommandName());
  }
}
