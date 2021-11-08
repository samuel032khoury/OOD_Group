package model.imagefile;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

import model.operation.IImageOperation;

/**
 * To represent an abstract image file using a 2-D Color array for data storing. It has a {@code
 * channelOperations} map to store operations that can be applied to each Color object.
 */
public final class ImageFileImpl implements ImageFile {
  private final Color[][] pixels;
  private final int height;
  private final int width;
  private final int maxColorVal;
  private final boolean alphaChannel;

  public ImageFile applyOperation(IImageOperation operation) {
    Color[][] newPixels = operation.apply(this.alphaChannel, this.pixels);
    int newMaxColorVal = operation.updateMaxColorVal(this.maxColorVal);
    boolean newAlphaChannel = operation.updateAlphaChannel(this.alphaChannel);
    return new ImageFileImpl(newPixels, newMaxColorVal, newAlphaChannel);
  }

//  protected Map<IChannelOperator, IChannelFunction> channelOperations;
  public ImageFileImpl(Color[][] pixels) {
    this(pixels, 255, false);
  }

  public ImageFileImpl(Color[][] pixels, int maxColorVal) {
    this(pixels, maxColorVal, false);
  }

  /**
   * To construct an image file that using a 2-D Color array present an image.
   *
   * @param pixels      the 2-D Color array that stores the data information for an image
   * @param maxColorVal the possible maximum value for image's color channel
   */
  public ImageFileImpl(Color[][] pixels, int maxColorVal, boolean alphaChannel) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0
            || this.twoDColorContainsNull(pixels)) {
      throw new IllegalArgumentException("Invalid Image!");
    }
    if (maxColorVal < 0) {
      throw new IllegalArgumentException("Invalid maximum color value for images!");
    }
    this.pixels = pixels;
    this.height = pixels.length;
    this.width = pixels[0].length;
    this.maxColorVal = maxColorVal;
    this.alphaChannel = alphaChannel;
//    this.channelOperations = new HashMap<>() {{
//        put(SingleChannelOperator.Red, (c -> {
//          final int red = c.getRed();
//          return new Color(red, red, red);
//        }));
//        put(SingleChannelOperator.Blue, (c -> {
//          final int blue = c.getBlue();
//          return new Color(blue, blue, blue);
//        }));
//        put(SingleChannelOperator.Green, (c -> {
//          final int green = c.getGreen();
//          return new Color(green, green, green);
//        }));
//        put(SimpleArithmeticChannelOperator.Intensity, (c -> {
//          final int intensity = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
//          return new Color(intensity, intensity, intensity);
//        }));
//        put(SimpleArithmeticChannelOperator.Value, (c -> {
//          final int value = Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
//          return new Color(value, value, value);
//        }));
//        put(SimpleArithmeticChannelOperator.Luma, (c -> {
//          final int luma = (int) (0.2126 * c.getRed() + 0.7152 * c.getGreen()
//                  + 0.0722 * c.getBlue());
//          return new Color(luma, luma, luma);
//        }));
//      }};
  }

  /**
   * to inspect if a 2-D Color array representing an image is broken (i.e. contains null)
   *
   * @param pixels the 2-D Color array stores Color information for an image
   * @return true if the 2-D Color array contains {@code null}
   */
  private boolean twoDColorContainsNull(Color[][] pixels) {
    boolean nullDetected = false;
    for (Color[] pixel : pixels) {
      if (pixel == null || nullDetected) {
        return true;
      }
      nullDetected = Arrays.asList(pixel).contains(null);
    }
    return nullDetected;
  }
//
//  /**
//   * generate a new vertical flipped copy of the image to which this method applies.
//   *
//   * @return a vertical flipped {@link ImageFile}.
//   */
//  @Override
//  public T vertiFlip() {
//    Color[][] vertiFlipped = new Color[this.height][];
//    for (int row = 0; row < this.height; row++) {
//      vertiFlipped[row] = pixels[this.height - 1 - row];
//    }
//    return this.genImage(vertiFlipped);
//  }
//
//  /**
//   * generate a new horizontal flipped copy of the image to which this method applies.
//   *
//   * @return a horizontal flipped {@link ImageFile}.
//   */
//  @Override
//  public T horizFlip() {
//    Color[][] horizFlipped = new Color[this.height][this.width];
//    for (int row = 0; row < this.height; row++) {
//      Color[] currRow = pixels[row];
//      for (int col = 0; col < this.width; col++) {
//        horizFlipped[row][col] = currRow[this.width - 1 - col];
//      }
//    }
//    return this.genImage(horizFlipped);
//  }
//
//  /**
//   * generate a new brightened copy of the image to which this method applies, with adjusted
//   * magnitude {@code value}.
//   *
//   * @param value the magnitude to adjust
//   * @return a brightened {@link ImageFile} if the value is positive, darkened if it's negative
//   */
//  @Override
//  public T brighten(int value) {
//    return this.adjustBrightness(value);
//  }
//
//  /**
//   * generate a new darkened copy of the image to which this method applies, with adjust magnitude
//   * {@code value}.
//   *
//   * @param value the magnitude to adjust
//   * @return a darkened {@link ImageFile} if the value is positive, brightened if it's negative
//   */
//  @Override
//  public T darken(int value) {
//    return this.adjustBrightness(-value);
//  }
//
//  /**
//   * generate a new brightened/darkened copy of the image to which this method applies, with adjust
//   * magnitude {@code value}.
//   *
//   * @param value the magnitude of brightness adjustment
//   * @return a brightened/darkened {@link ImageFile} depends on the given value
//   */
//  private T adjustBrightness(int value) {
//    Color[][] adjusted = new Color[this.height][this.width];
//    for (int row = 0; row < this.height; row++) {
//      for (int col = 0; col < this.width; col++) {
//        Color currColor = pixels[row][col];
//        int newR = Math.max(0, Math.min(255, currColor.getRed() + value));
//        int newG = Math.max(0, Math.min(255, currColor.getGreen() + value));
//        int newB = Math.max(0, Math.min(255, currColor.getBlue() + value));
//        adjusted[row][col] = new Color(newR, newG, newB);
//      }
//    }
//    return this.genImage(adjusted);
//  }
//
//  /**
//   * generate a new greyscale copy of the image to which this method applies, with the provided
//   * {@link IChannelOperator} applied.
//   *
//   * @param operator a {@link IChannelOperator} being applied on the original image.
//   * @return a greyscale {@link ImageFile} derived by applying the provided {@link IChannelOperator}
//   *              to the original image
//   * @throws IllegalArgumentException if the {@link IChannelOperator} is unsupported
//   */
//  @Override
//  public T greyscale(IChannelOperator operator) throws IllegalStateException {
//    Color[][] greyScaled = new Color[this.height][this.width];
//    if (!this.channelOperations.containsKey(operator)) {
//      throw new IllegalStateException("No such an operator can be found!");
//    }
//    final IChannelFunction function = this.channelOperations.get(operator);
//    for (int row = 0; row < this.height; row++) {
//      for (int col = 0; col < this.width; col++) {
//        Color currColor = pixels[row][col];
//        Color scaledColor = function.apply(currColor);
//        greyScaled[row][col] = scaledColor;
//      }
//    }
//    return this.genImage(greyScaled);
//  }
//
//  /**
//   * generate an identical copy of the image to which this method applies.
//   *
//   * @return an identical {@link ImageFile}.
//   */
//  @Override
//  public T copyImage() {
//    return this.genImage(this.pixels);
//  }
//
//  public abstract T genImage(Color[][] data);

  /**
   * get the height of an image represented by {@link ReadOnlyImageFile}.
   *
   * @return the height of an image
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * get the width of an image represented by {@link ReadOnlyImageFile}.
   *
   * @return the width of an image
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * get the possible maximum value of color channel of an image represented by {@link
   * ReadOnlyImageFile}.
   *
   * @return the possible maximum value of color channel of an image
   */
  @Override
  public int getMaxColorVal() {
    return this.maxColorVal;
  }

  /**
   * determine if the {@link ReadOnlyImageFile} object support alpha channel.
   *
   * @return true if the alpha channel is open, false if it's closed.
   */
  @Override
  public boolean alpha() {
    return alphaChannel;
  }

  /**
   * get the {@link Color} of an image represented by {@link ReadOnlyImageFile} at a pixel
   * determined by {@code row} and {@code col}.
   *
   * @return the color at the specified pixel
   */
  @Override
  public Color getColorAt(int row, int col) {
    Color selected = this.pixels[row][col];
    return new Color(selected.getRGB());
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare.
   * @return true if this object is the same as the obj argument; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImageFileImpl that = (ImageFileImpl) o;

    if (height != that.height || width != that.width || this.alphaChannel != that.alphaChannel || this.maxColorVal != that.maxColorVal) {
      return false;
    }

    return Arrays.deepEquals(pixels, that.pixels);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code determined by the information stored by an image.
   */
  @Override
  public int hashCode() {
    int pixelHash = Arrays.deepHashCode(pixels);
    return Objects.hash(pixelHash, height, width, this.getMaxColorVal(), this.alpha());
  }
}
