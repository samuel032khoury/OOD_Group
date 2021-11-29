package controller.handler.geometric;

import model.image.operation.ImageOperation;
import model.image.operation.geometric.HorizontalFlipOperation;

/**
 * Handles the "horizontal-flip" command, which flips (reflects) a given image relative to its
 * vertical axis.
 */
public class HorizontalFlipCommandHandler extends AbstractGeometricCommandHandler {

  @Override
  public String getCommandName() {
    return "horizontal-flip";
  }

  @Override
  protected ImageOperation getOperation() {
    return new HorizontalFlipOperation();
  }
}
