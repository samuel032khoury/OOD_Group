package model.histogram;

import model.image.Image;
import model.image.pixel.Pixel;

/**
 * Represents the histogram of pixel values distribution based on the average intensity of a pixel's
 * channels. Note that the average intensity is rounded down to the int value, as we are
 * representing a distribution of discrete values, not continuous.
 */
public class IntensityHistogram extends AHistogram {

  /**
   * Creates a new intensity-based histogram based on the supplied image and min/max values.
   *
   * @param img      the image to graph a histogram on
   * @param minValue the minimum value to include in the histogram (the lower bound on range)
   * @param maxValue the maximum value to include in the histogram (the upper bound on range)
   */
  public IntensityHistogram(Image img, int minValue, int maxValue) {
    super(img, minValue, maxValue);
  }

  /**
   * Creates a new intensity-based histogram on the supplied image using the default range as
   * defined in the AHistogram.
   *
   * @param img the image to get the histogram from
   */
  public IntensityHistogram(Image img) {
    super(img);
  }

  @Override
  protected int getProcessingValue(Pixel px) {
    // we can simply do int division here, as we are basically choosing to round everything down
    return (px.getRed() + px.getGreen() + px.getBlue()) / 3;
  }
}
