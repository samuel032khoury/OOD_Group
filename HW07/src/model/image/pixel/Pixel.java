package model.image.pixel;

/**
 * Defines all the operations that pertain to a single-colored RGB pixel. This interface covers all
 * the operations that pertain to an RGB pixel, but may be extended to get other pixel
 * implementations such as RGB-transparent or CMYK-encoded pixels.
 */
public interface Pixel {

  /**
   * Gets the red channel value.
   *
   * @return the red channel value
   */
  int getRed();

  /**
   * Gets the green channel value.
   *
   * @return the green channel value.
   */
  int getGreen();

  /**
   * Gets the blue channel value.
   *
   * @return the blue channel value
   */
  int getBlue();

  /**
   * Updates the red value of the current pixel by the supplied value. Note that the bounds are
   * enforced; if the updateValue will exceed either the MIN or the MAX bound, the red will take the
   * corresponding MIN/MAX value.
   *
   * @param redValue the value by which to update the red channel
   * @return the new value of the red channel
   */
  int setRed(int redValue);


  /**
   * Updates the blue value of the current pixel by the supplied value. Note that the bounds are
   * enforced; if the updateValue will exceed either the MIN or the MAX bound, the blue will take
   * the corresponding MIN/MAX value.
   *
   * @param blueValue the value by which to update the blue channel
   * @return the new value of the blue channel
   */
  int setBlue(int blueValue);

  /**
   * Updates the green value of the current pixel by the supplied value. Note that the bounds are
   * enforced; if the updateValue will exceed either the MIN or the MAX bound, the green will take
   * the corresponding MIN/MAX value.
   *
   * @param greenValue the value by which to update the green channel
   * @return the new value of the green channel
   */
  int setGreen(int greenValue);

  /**
   * Returns a copy of this pixel for mutation.
   *
   * @return A copy of the current pixel.
   */
  Pixel copy();
}
