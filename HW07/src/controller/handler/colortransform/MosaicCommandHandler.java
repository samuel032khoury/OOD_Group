package controller.handler.colortransform;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import controller.handler.ImageProcessCommandHandler;
import model.image.Image;
import model.image.operation.colortransform.MosaicOperation;

/**
 * Handles the command "mosaic".
 *
 * <p>"mosaic" command applies the mosaic effect onto the given image. More specifically, given an
 * integer as the mosaic seed, it randomly generates a corresponding number of {@link
 * MosaicOperation.SeedNode} on the image, and partitions the image into
 * (at most) that amount fragments (tiles) has the same color, by clustering every pixel to its
 * nearest {@link MosaicOperation.SeedNode}.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link MosaicOperation}.
 */
public class MosaicCommandHandler implements ImageProcessCommandHandler {

  @Override
  public void process(List<String> args,
      Function<String, Image> getImage,
      BiConsumer<String, Image> addImage) throws IllegalArgumentException {
    if (args.size() != 3) {
      throw new IllegalArgumentException(
          String.format("Expected 3 arguments, received %d", args.size()));
    }

    String imageName = args.get(0);
    String targetImageName = args.get(1);

    int seedNum = Integer.parseInt(args.get(2));

    // get the image from the cached list
    Image img = getImage.apply(imageName);
    addImage.accept(targetImageName,
        new MosaicOperation(seedNum).apply(img));
  }

  @Override
  public String getCommandName() {
    return "mosaic";
  }

  @Override
  public String getHelpText() {
    return getCommandName() + " [image-name] [dest-image-name] [seed]";
  }
}
