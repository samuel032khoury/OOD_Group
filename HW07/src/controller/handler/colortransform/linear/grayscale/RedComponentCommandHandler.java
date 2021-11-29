package controller.handler.colortransform.linear.grayscale;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.linear.grayscale.RedGrayscaleOperation;

/**
 * Handles the command "red-component".
 *
 * <p>"red-component" is a grayscale-based command; that means that every channel in a pixel in
 * the resultant image will be set to the same value -- the value for the red channel for that
 * pixel. For example, a pixel (0, 50, 255) will become (0, 0, 0), and a pixel (31, 31, 30) will
 * become (31, 31, 31). Such operation is performed individually on every pixel in the image.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link RedGrayscaleOperation}.
 */
public class RedComponentCommandHandler extends AbstractComponentCommandHandler {

  @Override
  protected ImageOperation getOperation() {
    return new RedGrayscaleOperation();
  }

  @Override
  public String getCommandName() {
    return "red-component";
  }
}
