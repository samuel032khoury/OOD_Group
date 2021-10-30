package model;

import java.awt.Color;
import java.util.Arrays;

public class ImageFilePPM implements ImageFile {
  private final Color[][] pixels;
  private final int height;
  private final int width;

  public ImageFilePPM(Color[][] pixels) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0
            || this.TwoDColorContainsNull(pixels)) {
      throw new IllegalArgumentException("Invalid Image");
    }
    this.pixels = pixels;
    this.height = pixels.length;
    this.width = pixels[0].length;
  }

  private boolean TwoDColorContainsNull(Color[][] pixels) {
    boolean nullDetected = false;
    for (Color[] pixel : pixels) {
      if (pixel == null || nullDetected) {
        return true;
      }
      nullDetected = Arrays.asList(pixel).contains(null);
    }
    return nullDetected;
  }

  @Override
  public ImageFile vertiFlip() {
    Color[][] vertiFlipped = new Color[this.height][];
    for (int row = 0; row < this.height; row++) {
      vertiFlipped[row] = pixels[this.height - 1 - row];
    }
    return new ImageFilePPM(vertiFlipped);
  }

  @Override
  public ImageFile horizFlip() {
    Color[][] horizFlipped = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      Color[] currRow = pixels[row];
      for (int col = 0; col < this.width; col++) {
        horizFlipped[row][col] = currRow[this.width - 1 - col];
      }
    }
    return new ImageFilePPM(horizFlipped);
  }

  @Override
  public ImageFile brighten(int value) {
    return this.adjustBrightness(value);
  }

  @Override
  public ImageFile darken(int value) {
    return this.adjustBrightness(-value);
  }

  private ImageFile adjustBrightness(int value) {
    Color[][] adjusted = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        int newR = Math.max(0, Math.min(255, currColor.getRed() + value));
        int newG = Math.max(0, Math.min(255, currColor.getGreen() + value));
        int newB = Math.max(0, Math.min(255, currColor.getBlue() + value));
        adjusted[row][col] = new Color(newR,newG,newB);
      }
    }
    return new ImageFilePPM(adjusted);
  }

  @Override
  public ImageFile greyscale(PixelChannel pixelChannel) {
    return null;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public ImageFile getImage() {
    return this;
  }

  @Override
  public ReadOnlyImageFile copy() {
    return new ImageFilePPM(this.pixels);
  }
}
