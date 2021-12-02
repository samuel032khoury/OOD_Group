package controller.handler.colortransform;

import controller.handler.ImageProcessCommandHandler;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;
import model.image.operation.colortransform.BrightenOperation;

/**
 * Handles the command "brighten".
 *
 * <p>"brighten" command brightens/darkens (depending on the arguments) a given image. More
 * specifically, given some value between -255..255, it increases or decreases the value of every
 * pixel's channels by that amount. If, after the addition/subtraction, the channel value exceeds
 * the standard bounds (0..255), it is defaulted to the bounds. For example, if we brighten the
 * pixel (120, 254, 30) by value 40, we will get the pixel (160, 255, 70). Similarly, if we darken
 * the pixel (30, 40, 0) by 10, we will get the pixel (20, 30, 0).
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link BrightenOperation}.
 */
public class BrightenCommandHandler implements ImageProcessCommandHandler {

  @Override
  public void process(List<String> args,
      Function<String, Image> getImage,
      BiConsumer<String, Image> addImage) throws IllegalArgumentException {
    if (args.size() != 3) {
      throw new IllegalArgumentException(
          String.format("Expected 3 arguments, received %d", args.size()));
    }

    String imageName = args.get(0);

    int brightenAmount = Integer.parseInt(args.get(2));

    if (brightenAmount <= -255 || brightenAmount >= 255) {
      throw new IllegalArgumentException(
          "Can not brighten/darken an image by a number outside the valid RGB range [-255, 255]");
    }

    String targetImageName = args.get(1);

    if (imageName.isEmpty() || targetImageName.isEmpty()) {
      throw new IllegalArgumentException("Image names cannot be empty");
    }

    // get the image from the cached list
    Image img = getImage.apply(imageName);
    addImage.accept(targetImageName,
        new BrightenOperation(brightenAmount) .apply(img));
  }

  @Override
  public String getCommandName() {
    return "brighten";
  }

  @Override
  public String getHelpText() {
    return getCommandName() + " [image-name] [dest-image-name] [increment]";
  }
}
