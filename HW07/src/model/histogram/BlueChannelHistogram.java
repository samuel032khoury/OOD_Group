package model.histogram;

import model.image.Image;
import model.image.pixel.Pixel;

/**
 * Represents the histogram of pixel values distribution based on the blue channel of a pixel.
 */
public class BlueChannelHistogram extends AHistogram {

  /**
   * Creates a new blue channel histogram based on the supplied image and min/max values.
   *
   * @param img      the image to graph a histogram on
   * @param minValue the minimum value to include in the histogram (the lower bound on range)
   * @param maxValue the maximum value to include in the histogram (the upper bound on range)
   */
  public BlueChannelHistogram(Image img, int minValue, int maxValue) {
    super(img, minValue, maxValue);
  }

  /**
   * Creates a new blue channel histogram on the supplied image using the default range as defined
   * in the AHistogram.
   *
   * @param img the image to get the histogram from
   */
  public BlueChannelHistogram(Image img) {
    super(img);
  }

  @Override
  protected int getProcessingValue(Pixel px) {
    return px.getBlue();
  }
}
