package model.histogram;

import java.util.Map;

/**
 * Describes a set of public operations that could be performed on histograms.
 */
public interface IHistogram {

  /**
   * Returns a map of counts for this histogram where the keys are the pixel values, and the values
   * are the counts for that pixel value.
   *
   * @return the map of counts
   */
  Map<Integer, Integer> getCounts();
}
