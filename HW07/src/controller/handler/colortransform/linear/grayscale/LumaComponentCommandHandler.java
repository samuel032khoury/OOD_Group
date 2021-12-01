package controller.handler.colortransform.linear.grayscale;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.linear.grayscale.LumaGrayscaleOperation;

/**
 * Handles the command "luma-component".
 *
 * <p>"luma-component" is a grayscale-based command; that means that every channel in a pixel in
 * the resultant image will be set to the same value -- rounded 0.2126 * r + 0.7152 * g + 0.0722 * b
 * for an (r, g, b) pixel. For example, a pixel (0, 50, 255) will become (54, 54, 54), and a pixel
 * (31, 31, 30) will become (31, 31, 31). Such operation is performed individually on every pixel in
 * the image.
 *
 * <p>This is, however, a command handler, which means that this class will parse the command and
 * delegate the actual transformation process to {@link LumaGrayscaleOperation}.
 */
public class LumaComponentCommandHandler extends AbstractComponentCommandHandler {

  @Override
  public String getCommandName() {
    return "luma-component";
  }

  @Override
  protected ImageOperation getOperation() {
    return new LumaGrayscaleOperation();
  }
}