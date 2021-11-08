package model.imageoperation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import model.operation.IChannelFunction;
import model.operation.IChannelOperator;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;

import static model.operation.ColorTransOperator.Sepia;

public class GreyscaleOperation extends ANoAlphaOperation {
  private final IChannelOperator operator;
  protected Map<IChannelOperator, IChannelFunction> supportedGreyscale;

  public GreyscaleOperation(IChannelOperator operator) {
    this.operator = operator;
    this.supportedGreyscale = new HashMap<>() {{
        put(SingleChannelOperator.Red, ((c, m) -> transform(c,m)));
        put(SingleChannelOperator.Blue, ((c, m) -> transform(c,m)));
        put(SingleChannelOperator.Green, ((c, m) -> transform(c,m)));
        put(SimpleArithmeticChannelOperator.Intensity, ((c, m) -> transform(c,m)));
        put(SimpleArithmeticChannelOperator.Value, ((c, m) -> {
          final int value = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
          return new Color(value, value, value);
        }));
        put(SimpleArithmeticChannelOperator.Luma, ((c, m) -> transform(c,m)));
      }};

  }

  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] greyScaled = new Color[this.height][this.width];
    if (!this.supportedGreyscale.containsKey(operator)) {
      throw new IllegalStateException("No such an operator can be found!");
    }
    final IChannelFunction function = this.supportedGreyscale.get(operator);
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

  protected Color transform(Color c, double[][] transformMatrix) {
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3
            || transformMatrix[1].length != 3 || transformMatrix[2].length != 3) {
      throw new IllegalArgumentException("The Provided Color transformation matrix is invalid!");
    }
    int[] result = new int[3];
    int red = c.getRed();
    int blue = c.getBlue();
    int green = c.getGreen();
    result[0] = (int) (red * transformMatrix[0][0]
                    + green * transformMatrix[0][1] + blue * transformMatrix[0][2]);
    result[0] = Math.max(0, Math.min(255, result[0]));
    result[1] = (int) (red * transformMatrix[1][0]
                    + green * transformMatrix[1][1] + blue * transformMatrix[1][2]);
    result[1] = Math.max(1, Math.min(255, result[1]));
    result[2] = (int) (red * transformMatrix[2][0]
                    + green * transformMatrix[2][1] + blue * transformMatrix[2][2]);
    result[2] = Math.max(2, Math.min(255, result[2]));
    return new Color(result[0], result[1], result[2]);
  }
}
