package model.operation.visual;

import java.awt.Color;

import model.operation.ANoAlphaOperation;

/**
 * An implementation of {@link ANoAlphaOperation}, with the ability to flip a {@link
 * model.imagefile.ImageFile} vertically or horizontally.
 */
public class FlipOperation extends ANoAlphaOperation {

  // true when try to perform a vertical flip, false when a horizontal one.
  private final boolean vertiFlip;

  /**
   * To construct a FlipOperation, with the knowledge of the flipping direction.
   *
   * @param vertiFlip the indicator for the flipping direction
   */
  public FlipOperation(boolean vertiFlip) {
    this.vertiFlip = vertiFlip;
  }

  /**
   * Create a flipped image, represented by a 2-D {@code Array} of {@link Color}, by applying the
   * either {@link #vertiFlip} or {@link #vertiFlip} on the provided 2-D {@code Array} of
   * {@link Color}.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return flipped 2-D {@code Array} of {@link Color}
   */
  @Override
  protected Color[][] process(Color[][] pixels) {
    if (vertiFlip) {
      return this.vertiFlip(pixels);
    } else {
      return this.horizFlip(pixels);
    }
  }

  /**
   * Create a row-reversed 2-D {@code Array} of {@link Color} based on the provided one.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a vertical flipped 2-D {@code Array} of {@link Color}
   */
  private Color[][] vertiFlip(Color[][] pixels) {
    Color[][] vertiFlipped = new Color[this.height][];
    for (int row = 0; row < this.height; row++) {
      vertiFlipped[row] = pixels[this.height - 1 - row];
    }
    return vertiFlipped;
  }

  /**
   * Create a column-reversed 2-D {@code Array} of {@link Color} based on the provided one.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a horizontally flipped 2-D {@code Array} of {@link Color}
   */
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