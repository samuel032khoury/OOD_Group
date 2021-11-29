package model.image.pixel;

import java.util.Objects;

/**
 * Defines a standard (reg, green, blue) pixel. See https://en.wikipedia.org/wiki/RGB_color_model to
 * understand RGB color model better.
 */
public class RGBPixel implements Pixel {

  private int red; // stores a numerical 0-255 value denoting the red color channel
  private int green; // stores a numerical 0-255 value denoting the green color channel
  private int blue; // stores a numerical 0-255 value denoting the blue color channel

  private static final int MIN_CHANNEL_VALUE = 0;
  private static final int MAX_CHANNEL_VALUE = 255;

  /**
   * Constructs a new RGB pixel from the supplied color channel values. All the values must be
   * between 0 and 255 inclusive.
   *
   * @param red   the red channel value
   * @param green the green channel value
   * @param blue  the blue channel value
   * @throws IllegalArgumentException if any of the values do not pass the validation check
   */
  public RGBPixel(int red, int green, int blue) throws IllegalArgumentException {
    this.red = validateChannel(red, "red");
    this.green = validateChannel(green, "green");
    this.blue = validateChannel(blue, "blue");
  }

  @Override
  public Pixel copy() {
    return new RGBPixel(red, green, blue);
  }

  @Override
  public int getRed() {
    return red; // immutable, no need to worry about someone setting it directly
  }

  @Override
  public int getGreen() {
    return green; // immutable, no need to worry about someone setting it directly
  }

  @Override
  public int getBlue() {
    return blue; // immutable, no need to worry about someone setting it directly
  }

  @Override
  public int setRed(int updateValue) {
    // using minmax to enforce the min channel...max channel bounds
    red = Math.max(MIN_CHANNEL_VALUE, Math.min(MAX_CHANNEL_VALUE, updateValue));

    return getRed();
  }

  @Override
  public int setGreen(int updateValue) {
    // using minmax to enforce the min channel...max channel bounds
    green = Math.max(MIN_CHANNEL_VALUE, Math.min(MAX_CHANNEL_VALUE, updateValue));

    return getGreen();
  }

  @Override
  public int setBlue(int updateValue) {
    // using minmax to enforce the min channel...max channel bounds
    blue = Math.max(MIN_CHANNEL_VALUE, Math.min(MAX_CHANNEL_VALUE, updateValue));

    return getBlue();
  }

  /**
   * Validator method for the constructor arguments for this pixel. To be used during primary value
   * assignment.
   *
   * <p>For an RGB pixel, the value of a specific channel must be between 0 and 255 inclusive,
   * otherwise this validator should throw an exception. Note that we do not need to test for null
   * arguments, as java simply would not allow {@code null} to be passed in place of {@code int}.
   *
   * @param value       the value of the specific channel
   * @param channelName the name of the channel to be displayed in the error message
   * @return the supplied value if the value passes the validation checks
   * @throws IllegalArgumentException if the value does not pass the validation check
   */
  private static int validateChannel(int value, String channelName)
      throws IllegalArgumentException {
    if (value < MIN_CHANNEL_VALUE || value > MAX_CHANNEL_VALUE) {
      throw new IllegalArgumentException(
          String.format(
              "The %s channel must be between [%d, %d], got %d.",
              channelName,
              MIN_CHANNEL_VALUE,
              MAX_CHANNEL_VALUE,
              value)
      );
    }

    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RGBPixel rgbPixel = (RGBPixel) o;
    return getRed() == rgbPixel.getRed() && getGreen() == rgbPixel.getGreen()
        && getBlue() == rgbPixel.getBlue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRed(), getGreen(), getBlue());
  }
}
