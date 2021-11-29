package controller.handler.colortransform.linear;

import controller.handler.AbstractCommandHandler;
import model.image.operation.ImageOperation;
import model.image.operation.colortransform.linear.SepiaOperation;

/**
 * Handles the command "sepia".
 */
public class SepiaCommandHandler extends AbstractCommandHandler {

  @Override
  public String getCommandName() {
    return "sepia";
  }

  @Override
  protected ImageOperation getOperation() {
    return new SepiaOperation();
  }
}
