package controller.handler.colortransform.linear.grayscale;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.linear.grayscale.IntensityGrayscaleOperation;

/**
 * Handles the command "intensity-component".
 *
 * <p>"intensity-component" is a grayscale-based command; that means that every channel in a pixel
 * in the resultant image will be set to the same value -- the rounded (standard rounding rules)
 * average of the pixel's channels. For example, a pixel (0, 50, 255) will become (102, 102, 102),
 * and a pixel (31, 30, 30) will become (30, 30, 30). Such operation is performed individually on
 * every pixel in the image.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link IntensityGrayscaleOperation}.
 */
public class IntensityComponentCommandHandler extends AbstractComponentCommandHandler {

  @Override
  public String getCommandName() {
    return "intensity-component";
  }

  @Override
  protected ImageOperation getOperation() {
    return new IntensityGrayscaleOperation();
  }
}