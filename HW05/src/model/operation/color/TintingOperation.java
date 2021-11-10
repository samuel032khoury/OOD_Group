package model.operation.color;

import model.operation.opertor.colortrans.TiltingOperator;
import model.operation.opertor.colortrans.IColorTransOperator;

//TODO
/**
 * A concrete class of AColorTransformOperation, documented several available filters
 * related to complex color transforms.
 * This will be operated on ImageFile to get a colored version of images.
 */
public class TintingOperation extends AColorTransformOperation {

  //TODO
  public TintingOperation(IColorTransOperator operator) {
    super(operator);
    supportedOperation.put(TiltingOperator.Sepia, OperationUtil::transform);
  }
}