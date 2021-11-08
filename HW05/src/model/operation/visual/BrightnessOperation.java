package model.operation.visual;

import java.awt.Color;

import model.operation.ANoAlphaOperation;

public class BrightnessOperation extends ANoAlphaOperation {
  private final int value;

  public BrightnessOperation(boolean brighten, int value) {
    this.value = value * (brighten? 1 : -1);
  }

  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] adjusted = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        int newR = Math.max(0, Math.min(255, currColor.getRed() + value));
        int newG = Math.max(0, Math.min(255, currColor.getGreen() + value));
        int newB = Math.max(0, Math.min(255, currColor.getBlue() + value));
        adjusted[row][col] = new Color(newR, newG, newB);
      }
    }
    return adjusted;
  }
}
