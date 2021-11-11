package model.operation.visual;

import java.awt.*;

import model.operation.ANoAlphaOperation;

//TODO
public class FlipOperation extends ANoAlphaOperation {

  // true when try to perform a vertical flip, false when a horizontal one.
  private final boolean vertiFlip;

  //TODO
  public FlipOperation(boolean vertiFlip) {
    this.vertiFlip = vertiFlip;
  }

  //TODO
  @Override
  protected Color[][] process(Color[][] pixels) {
    if (vertiFlip) {
      return this.vertiFlip(pixels);
    } else {
      return this.horizFlip(pixels);
    }
  }

  //TODO
  private Color[][] vertiFlip(Color[][] pixels) {
    Color[][] vertiFlipped = new Color[this.height][];
    for (int row = 0; row < this.height; row++) {
      vertiFlipped[row] = pixels[this.height - 1 - row];
    }
    return vertiFlipped;
  }

  //TODO
  private Color[][] horizFlip(Color[][] pixels) {
    Color[][] horizFlipped = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      Color[] currRow = pixels[row];
      for (int col = 0; col < this.width; col++) {
        horizFlipped[row][col] = currRow[this.width - 1 - col];
      }
    }
    return horizFlipped;
  }
}
