package model.imageFile;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.operation.IChannelFunction;
import model.operation.IChannelOperator;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;

public abstract class AImageFile implements ImageFile {
  protected final Color[][] pixels;
  protected final int height;
  protected final int width;
  protected boolean alphaChannel;

  protected Map<IChannelOperator, IChannelFunction> channelOperations;

  public AImageFile(Color[][] pixels) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0
            || this.TwoDColorContainsNull(pixels)) {
      throw new IllegalArgumentException("Invalid Image");
    }
    this.pixels = pixels;
    this.height = pixels.length;
    this.width = pixels[0].length;
    this.channelOperations = new HashMap<>() {{
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
        final int luma = (int) (0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue());
        return new Color(luma, luma, luma);
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
    return new ImageFileNoAlpha(vertiFlipped);
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
    return new ImageFileNoAlpha(horizFlipped);
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
    return new ImageFileNoAlpha(adjusted);
  }

  @Override
  public ImageFile copyImage() {
    return new ImageFileNoAlpha(this.pixels);
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
  public ImageFile greyscale(IChannelOperator operator) throws IllegalArgumentException{
    Color[][] greyScaled = new Color[this.height][this.width];
    if(!this.channelOperations.containsKey(operator)) {
      throw new IllegalArgumentException("Operator cannot be found!");
    }
    final IChannelFunction function = this.channelOperations.get(operator);
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        Color currColor = pixels[row][col];
        Color scaledColor = function.apply(currColor);
        greyScaled[row][col] = scaledColor;
      }
    }
    return new ImageFileNoAlpha(greyScaled);
  }
}
