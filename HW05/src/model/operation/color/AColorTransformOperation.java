package model.operation.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.function.IColorTransformFunction;
import model.operation.opertor.colortrans.IColorTransOperator;

/**
 * An abstract class that has the shared method to process the color transform operations.
 * A map has keys of {@link IColorTransOperator} is provided to retrieve appropriate
 * {@link IColorTransformFunction} applied to the image.
 */
public abstract class AColorTransformOperation extends ANoAlphaOperation {
  protected final IColorTransOperator operator;
  protected final Map<IColorTransOperator, IColorTransformFunction> supportedOperation;

  //TODO
  public AColorTransformOperation(IColorTransOperator operator) {
    this.operator = operator;
    supportedOperation = new HashMap<>();
  }

  //TODO
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] greyScaled = new Color[this.height][this.width];
    if (!this.supportedOperation.containsKey(operator)) {
      throw new IllegalStateException("No such an operator can be found!");
    }
    final IColorTransformFunction function = this.supportedOperation.get(operator);
    final double[][] transformMatrix = operator.getMatrix();
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        Color scaledColor = function.apply(currColor, transformMatrix);
        greyScaled[row][col] = scaledColor;
      }
    }
    return greyScaled;
  }
}
