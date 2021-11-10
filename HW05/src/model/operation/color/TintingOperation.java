package model.operation.color;

import java.awt.Color;

import model.imagefile.ImageFile;
import model.operation.opertor.colortrans.TiltingOperator;
import model.operation.opertor.colortrans.IColorTransOperator;


/**
 * An implementation of {@link AColorTransformOperation}, with several available (tinting)
 * operations added in the {@link #supportedOperation}, which can be applied to an {@link ImageFile}
 * to get a tinted version of images.
 */
public class TintingOperation extends AColorTransformOperation {

  /**
   * To construct a {@link TintingOperation}, which adds several tinting operations (along with the
   * corresponding rules/functions) to the {@link #supportedOperation}.
   *
   * @param operator the demanded operator for {@link #process(Color[][])})} to apply on a {@link
   *                 ImageFile}.
   */
  public TintingOperation(IColorTransOperator operator) {
    super(operator);
    supportedOperation.put(TiltingOperator.Sepia, OperationUtil::transform);
  }
}