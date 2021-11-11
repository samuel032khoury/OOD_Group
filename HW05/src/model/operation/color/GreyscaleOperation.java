package model.operation.color;

import java.awt.Color;

import model.imagefile.ImageFile;
import model.operation.opertor.colortrans.IColorTransOperator;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;
import model.operation.opertor.colortrans.SingleChannelGreyscaleOperator;

/**
 * An implementation of {@link AColorTransformOperation}, with several available (greyscale)
 * operations added in the {@link #supportedOperation}, which can be applied to an {@link ImageFile}
 * to get a greyscaled version of images.
 */
public class GreyscaleOperation extends AColorTransformOperation {

  /**
   * To construct a {@link GreyscaleOperation}, which adds several greyscale operations (along with
   * the corresponding rules/functions) to the {@link #supportedOperation}.
   *
   * @param operator the demanded operator for {@link #process(Color[][])} to apply on a {@link
   *                 ImageFile}.
   */
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
