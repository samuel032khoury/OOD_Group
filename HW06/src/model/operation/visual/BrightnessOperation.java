package model.operation.visual;

import java.awt.Color;

import model.operation.ANoAlphaOperation;
import model.operation.OperationUtil;

/**
 * An implementation of {@link ANoAlphaOperation}, with the ability to adjust the brightness of a
 * {@link model.imagefile.ImageFile}.
 */
public class BrightnessOperation extends ANoAlphaOperation {
  private final int value;

  /**
   * To construct a BrightnessOperation.
   *
   * @param brighten the indicator for the brightness adjustment direction.
   * @param value    the magnitude of brightness adjustment.
   */
  public BrightnessOperation(boolean brighten, int value) {
    this.value = value * (brighten ? 1 : -1);
  }

  /**
   * Create a brightened/darkened image, represented by a 2-D {@code Array} of {@link Color}, by
   * update the value for each channel of each Color in the  provided 2-D {@code Array} of {@link
   * Color}.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a new 2-D {@code Array} of {@link Color} with every channel of every {@link Color}
   *         increasing/decreasing the same magnitude
   */
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] adjusted = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        int[] newRGB = OperationUtil.produceValidColorValue(
                currColor.getRed() + value,
                currColor.getGreen() + value,
                currColor.getBlue() + value);
        adjusted[row][col] = new Color(newRGB[0], newRGB[1], newRGB[2], currColor.getAlpha());
      }
    }
    return adjusted;
  }
}
