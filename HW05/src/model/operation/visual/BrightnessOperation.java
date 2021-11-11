package model.operation.visual;

import java.awt.Color;

import model.operation.ANoAlphaOperation;
import model.operation.color.OperationUtil;

/**
 * An operation that changes the brightness of the image.
 */
public class BrightnessOperation extends ANoAlphaOperation {
  private final int value;

  /**
   * To construct a BrightnessOperation.
   * @param brighten if the operation is to brighten an image or darkens it.
   * @param value how much to adjust the brightness.
   */
  public BrightnessOperation(boolean brighten, int value) {
    this.value = value * (brighten ? 1 : -1);
  }

  /**
   * Apply the BrightnessOperation on a 2-D {@code Array} of {@link Color}.
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a processed 2-D {@code Array} of {@link Color}
   */
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] adjusted = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        int[] newRGB = OperationUtil.giveValidColorValue(
                currColor.getRed() + value,
                currColor.getGreen() + value,
                currColor.getBlue() + value);
        adjusted[row][col] = new Color(newRGB[0], newRGB[1], newRGB[2], currColor.getAlpha());
      }
    }
    return adjusted;
  }
}
