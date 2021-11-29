package controller.handler.colortransform.ranged;

import controller.handler.colortransform.AbstractComponentCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.ranged.BlurImageOperation;

/**
 * Implements the blur command. Blurring an image applies a filter to each channel of each pixel
 * that combines portions of the surrounding pixel's channel.
 */
public class BlurCommandHandler extends AbstractComponentCommandHandler {

  @Override
  protected ImageOperation getOperation() {
    return new BlurImageOperation();
  }

  @Override
  public String getCommandName() {
    return "blur";
  }
}
