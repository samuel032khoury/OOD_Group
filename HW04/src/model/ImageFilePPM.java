package model;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImageFilePPM implements ImageFile {
  private final Color[][] pixels;
  private final int height;
  private final int width;
  private Map<IChannelOperator, IGetChannelFunction> channelOperations;

  public ImageFilePPM(Color[][] pixels) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0
            || this.TwoDColorContainsNull(pixels)) {
      throw new IllegalArgumentException("Invalid Image");
    }
    this.pixels = pixels;
    this.height = pixels.length;
    this.width = pixels[0].length;
    this.channelOperations = new HashMap<>() {{
      put(CommonChannelOperator.Red, (c -> {
        int red = c.getRed();
        return new Color(red, red, red);
      }));
      put(CommonChannelOperator.Blue, (c -> {
        int blue = c.getBlue();
        return new Color(blue, blue, blue);
      }));
      put(CommonChannelOperator.Green, (c -> {
        int green = c.getGreen();
        return new Color(green, green, green);
      }));
      put(CommonChannelOperator.Intensity, (c -> {
        int intensity = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        return new Color(intensity, intensity, intensity);
      }));
      put(CommonChannelOperator.Value, (c -> {
        int value = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
        return new Color(value, value, value);
      }));
      put(CommonChannelOperator.Luma, (c -> {
        int luma = (int) (0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue());
        return new Color(luma,luma,luma);
      }));
    }};
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
        adjusted[row][col] = new Color(newR, newG, newB);
      }
    }
    return new ImageFilePPM(adjusted);
  }

  @Override
  public ImageFile greyscale(IChannelOperator pixelChannel) throws IllegalArgumentException{
    Color[][] greyScaled = new Color[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        Color scaledColor = channelOperations.get(pixelChannel).apply(currColor);
        greyScaled[row][col] = scaledColor;
      }
    }
    return new ImageFilePPM(greyScaled);
  }

  @Override
  public ImageFile copyImage() {
    return new ImageFilePPM(this.pixels);
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
  public ReadOnlyImageFile copyReadOnlyImageFile() {
    return this.copyImage();
  }
}
