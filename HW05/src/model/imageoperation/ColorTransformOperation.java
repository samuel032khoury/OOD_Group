package model.imageoperation;

import model.operation.ColorTransOperator;
import model.operation.IChannelOperator;

public class ColorTransformOperation  extends GreyscaleOperation{

  public ColorTransformOperation(IChannelOperator operator) {
    super(operator);
    supportedGreyscale.put(ColorTransOperator.Sepia, this::transform);
  }
}