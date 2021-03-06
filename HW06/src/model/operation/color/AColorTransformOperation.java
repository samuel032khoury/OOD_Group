package model.operation.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.OperationUtil;
import model.operation.function.IColorTransformFunction;
import model.operation.opertor.colortrans.IColorTransOperator;

/**
 * An abstract class that has the shared method to process the color transform operations. A map has
 * keys of {@link IColorTransOperator} is provided to retrieve appropriate {@link
 * IColorTransformFunction} applied to the image.
 */
public abstract class AColorTransformOperation extends ANoAlphaOperation {
  protected final IColorTransOperator operator;
  protected final Map<IColorTransOperator, IColorTransformFunction> supportedOperation;

  /**
   * To construct a {@link AColorTransformOperation} with the {@link IColorTransOperator} that saves
   * the transform rules as a matrix.
   *
   * @param operator the {@link IColorTransOperator} that saves the transform rules as a matrix.
   */
  public AColorTransformOperation(IColorTransOperator operator) {
    this.operator = operator;
    supportedOperation = new HashMap<>();
  }

  /**
   * Perform color transformation operations on the given {@code pixels}.
   * the rule of color transformation is provided by the {@link IColorTransformFunction},
   * delivered from the {@link #supportedOperation} by searching for the {@link IColorTransOperator}
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image being processed
   * @return a color transformed 2-D {@code Array} of {@link Color} representing an image,
   *         by the operation rule provided by {@link IColorTransformFunction}.
   */
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

  /**
   * Transform a pixel of color into another pixel of color, using color transform matrix.
   *
   * @param c               the input pixel of color.
   * @param transformMatrix the color transform matrix.
   * @return a transformed color pixels.
   */
  protected Color transform(Color c, double[][] transformMatrix) {
    int red = c.getRed();
    int blue = c.getBlue();
    int green = c.getGreen();
    int redTransformed = (int) (red * transformMatrix[0][0]
            + green * transformMatrix[0][1] + blue * transformMatrix[0][2]);
    int greenTransformed = (int) (red * transformMatrix[1][0]
            + green * transformMatrix[1][1] + blue * transformMatrix[1][2]);
    int blueTransformed = (int) (red * transformMatrix[2][0]
            + green * transformMatrix[2][1] + blue * transformMatrix[2][2]);
    int[] result = OperationUtil.produceValidColorValue(
            redTransformed, greenTransformed, blueTransformed);
    return new Color(result[0], result[1], result[2], c.getAlpha());
  }
}
