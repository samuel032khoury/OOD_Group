package model.imageoperation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import model.operation.IChannelFunction;
import model.operation.IChannelOperator;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;

public class GreyscaleOperation extends ANoAlphaOperation {
  private final IChannelOperator operator;
  protected Map<IChannelOperator, IChannelFunction> supportedGreyscale;

  public GreyscaleOperation(IChannelOperator operator) {
    this.operator = operator;
    this.supportedGreyscale = new HashMap<>() {{
        put(SingleChannelOperator.Red, (c -> {
          final int red = c.getRed();
          return new Color(red, red, red);
        }));
        put(SingleChannelOperator.Blue, (c -> {
          final int blue = c.getBlue();
          return new Color(blue, blue, blue);
        }));
        put(SingleChannelOperator.Green, (c -> {
          final int green = c.getGreen();
          return new Color(green, green, green);
        }));
        put(SimpleArithmeticChannelOperator.Intensity, (c -> {
          final int intensity = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
          return new Color(intensity, intensity, intensity);
        }));
        put(SimpleArithmeticChannelOperator.Value, (c -> {
          final int value = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
          return new Color(value, value, value);
        }));
        put(SimpleArithmeticChannelOperator.Luma, (c -> {
          final int luma = (int) (0.2126 * c.getRed() + 0.7152 * c.getGreen()
                  + 0.0722 * c.getBlue());
          return new Color(luma, luma, luma);
        }));
      }};

  }

  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] greyScaled = new Color[this.height][this.width];
    if (!this.supportedGreyscale.containsKey(operator)) {
      throw new IllegalStateException("No such an operator can be found!");
    }
    final IChannelFunction function = this.supportedGreyscale.get(operator);
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        Color scaledColor = function.apply(currColor);
        greyScaled[row][col] = scaledColor;
      }
    }
    return greyScaled;
  }
}
