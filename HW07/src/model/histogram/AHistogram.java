package model.histogram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.image.Image;
import model.image.pixel.Pixel;

/**
 * Represents an abstract histogram. // TODO: better docs
 */
public abstract class AHistogram implements IHistogram {

  private final Map<Integer, Integer> counts; // stores the counts per values

  /**
   * Creates a new histogram based on the supplied image.
   *
   * @param img      the image to graph a histogram on
   * @param minValue the minimum value to include in the histogram (the lower bound on range)
   * @param maxValue the maximum value to include in the histogram (the upper bound on range)
   */
  public AHistogram(Image img, int minValue, int maxValue) {
    counts = new HashMap<>();

    processCounts(img.getImageData());
  }

  /**
   * Creates a new histogram on the supplied image using the default range [0..255].
   *
   * @param img the image to get the histogram from
   */
  public AHistogram(Image img) {
    this(img, 0, 255);
  }

  /**
   * Processes the image data and updates the histogram counts.
   *
   * @param imageData the data to process, represented as a matrix of pixels
   */
  private void processCounts(List<List<Pixel>> imageData) {
    for (List<Pixel> row : imageData) {
      for (Pixel px : row) {
        int pxValue = getProcessingValue(px); // gets the processing value that we are counting

        // put the updated count in the frequency map
        counts.put(pxValue, counts.getOrDefault(pxValue, 0) + 1);
      }
    }
  }

  /**
   * Defines what value to process & count for a given pixel. This value can be either a pixel's
   * channel or some linear combination applied on the channels (intensity, etc.).
   *
   * @param px the pixel to get the value from
   */
  protected abstract int getProcessingValue(Pixel px);

  @Override
  public Map<Integer, Integer> getCounts() {
    return new HashMap<>(this.counts); // making a copy
  }
}
