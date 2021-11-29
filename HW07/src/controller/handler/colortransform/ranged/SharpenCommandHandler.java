package controller.handler.colortransform.ranged;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.ranged.SharpenImageOperation;

/**
 * Implements the sharpen command. Sharpening an image applies a filter to each channel of each
 * pixel that combines portions of the surrounding pixel's channel.
 */
public class SharpenCommandHandler extends AbstractComponentCommandHandler {

  @Override
  protected ImageOperation getOperation() {
    return new SharpenImageOperation();
  }

  @Override
  public String getCommandName() {
    return "sharpen";
  }
}
