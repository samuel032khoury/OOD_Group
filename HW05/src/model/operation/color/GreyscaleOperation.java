package model.operation.color;

import java.awt.Color;

import model.operation.opertor.colortrans.IColorTransOperator;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;
import model.operation.opertor.colortrans.SingleChannelGreyscaleOperator;

//TODO
/**
 * A concrete class of AColorTransformOperation, documented several available filters
 * related to simple color transforms.
 * This will be operated on ImageFile to get a colored version of images.
 */
public class GreyscaleOperation extends AColorTransformOperation {

  //TODO
  public GreyscaleOperation(IColorTransOperator operator) {
    super(operator);
    supportedOperation.put(SingleChannelGreyscaleOperator.Red, (OperationUtil::transform));
    supportedOperation.put(SingleChannelGreyscaleOperator.Blue, (OperationUtil::transform));
    supportedOperation.put(SingleChannelGreyscaleOperator.Green, (OperationUtil::transform));
    supportedOperation.put(SimpleArithmeticGreyscaleOperator.Intensity,
            (OperationUtil::transform));
    supportedOperation.put(SimpleArithmeticGreyscaleOperator.Value, ((c, m) -> {
      final int value = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
      return new Color(value, value, value);
    }));
    supportedOperation.put(SimpleArithmeticGreyscaleOperator.Luma,
            (OperationUtil::transform));

  }


}
