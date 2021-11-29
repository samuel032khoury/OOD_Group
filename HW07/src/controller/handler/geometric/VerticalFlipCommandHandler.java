package controller.handler.geometric;

import model.image.operation.ImageOperation;
import model.image.operation.geometric.VerticalFlipOperation;

/**
 * Handles the "vertical-flip" command, which flips (reflects) a given image relative to its
 * horizontal axis.
 */
public class VerticalFlipCommandHandler extends AbstractGeometricCommandHandler {

  @Override
  public String getCommandName() {
    return "vertical-flip";
  }

  @Override
  protected ImageOperation getOperation() {
    return new VerticalFlipOperation();
  }
}
