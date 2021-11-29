package controller.handler.colortransform.linear.grayscale;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.linear.grayscale.GreenGrayscaleOperation;

/**
 * Handles the command "green-component".
 *
 * <p>"green-component" is a grayscale-based command; that means that every channel in a pixel in
 * the resultant image will be set to the same value -- the value for the green channel for that
 * pixel. For example, a pixel (0, 50, 255) will become (50, 50, 50), and a pixel (31, 31, 30) will
 * become (31, 31, 31). Such operation is performed individually on every pixel in the image.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link GreenGrayscaleOperation}.
 */
public class GreenComponentCommandHandler extends AbstractComponentCommandHandler {

  @Override
  protected ImageOperation getOperation() {
    return new GreenGrayscaleOperation();
  }

  @Override
  public String getCommandName() {
    return "green-component";
  }
}
