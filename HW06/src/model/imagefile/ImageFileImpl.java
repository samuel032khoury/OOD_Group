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

  /**
   * Create an image with customized pixels.
   *
   * @param pixels the 2-D Color array that stores the data information for an image
   */
  public ImageFileImpl(Color[][] pixels) {
    this(pixels, 255, false);
  }

  /**
   * A constructor with customized pixels and possible maximum color value.
   *
   * @param pixels      the 2-D Color array that stores the data information for an image
   * @param maxColorVal the possible maximum value for image's color channel
   */
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
  }

  /**
   * Operate on an image base on the operation used.
   *
   * @param operation the operation used
   * @return a new ImageFile with the operation done on the ImageFile matrix.
   */
  public ImageFile applyOperation(IImageOperation operation) {
    Color[][] newPixels = operation.apply(this.alphaChannel, this.pixels);
    int newMaxColorVal = operation.updateMaxColorVal(this.maxColorVal);
    boolean newAlphaChannel = operation.updateAlphaChannel(this.alphaChannel);
    return new ImageFileImpl(newPixels, newMaxColorVal, newAlphaChannel);
  }

  /**
   * Deep copy a {@link ImageFile} object with duplicated information.
   *
   * @return a copy of the image.
   */
  @Override
  public ImageFile copy() {
    Color[][] copied = new Color[this.height][width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        copied[i][j] = new Color(pixels[i][j].getRGB(), true);
      }
    }
    return new ImageFileImpl(copied, this.maxColorVal, this.alphaChannel);
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
    return new Color(selected.getRGB(), true);
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

    if (height != that.height || width != that.width ||
            this.alphaChannel != that.alphaChannel || this.maxColorVal != that.maxColorVal) {
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
