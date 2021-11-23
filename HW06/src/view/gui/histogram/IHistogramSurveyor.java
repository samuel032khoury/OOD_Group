package view.gui.histogram;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent a surveyor that updates the data for drawing a histogram.
 */
public interface IHistogramSurveyor {
  /**
   * Provided a {@link ReadOnlyImageFile}, update the histogram data based on it.
   *
   * @param currImageFile the {@link ReadOnlyImageFile} that is inspecting
   */
  void updateHistogramData(ReadOnlyImageFile currImageFile);
}
