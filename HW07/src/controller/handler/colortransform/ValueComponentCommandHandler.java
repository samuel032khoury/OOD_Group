package controller.handler.colortransform;

import model.image.operation.ImageOperation;
import model.image.operation.colortransform.ValueGrayscaleOperation;

/**
 * Handles the command "value-component".
 *
 * <p>"value-component" is a grayscale-based command; that means that every channel in a pixel in
 * the resultant image will be set to the same value -- the maximum value between the pixel's
 * channels. For example, a pixel (0, 50, 255) will become (255, 255, 255), and a pixel (31, 31, 30)
 * will become (31, 31, 31). Such operation is performed individually on every pixel in the image.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link ValueGrayscaleOperation}.
 */
public class ValueComponentCommandHandler extends AbstractComponentCommandHandler {

  @Override
  public String getCommandName() {
    return "value-component";
  }

  @Override
  protected ImageOperation getOperation() {
    return new ValueGrayscaleOperation();
  }
}
