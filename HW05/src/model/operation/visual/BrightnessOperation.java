package model.operation.visual;

import java.awt.*;

import model.operation.ANoAlphaOperation;
import model.operation.color.OperationUtil;


//TODO
public class BrightnessOperation extends ANoAlphaOperation {
  private final int value;

  //TODO
  public BrightnessOperation(boolean brighten, int value) {
    this.value = value * (brighten ? 1 : -1);
  }

  //TODO
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
