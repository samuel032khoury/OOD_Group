package view.gui.histogram;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent a surveyor that updates the data for drawing a histogram.
 */
public interface IHistogramSurveyor {
  /**
   * TODO.
   */
  void updateHistogramData(ReadOnlyImageFile currImageFile);
}
