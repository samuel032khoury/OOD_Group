package model.operation.color;

import model.operation.opertor.colortrans.TiltingOperator;
import model.operation.opertor.colortrans.IColorTransOperator;

//TODO
public class TintingOperation extends AColorTransformOperation {

  //TODO
  public TintingOperation(IColorTransOperator operator) {
    super(operator);
    supportedOperation.put(TiltingOperator.Sepia, OperationUtil::transform);
  }
}