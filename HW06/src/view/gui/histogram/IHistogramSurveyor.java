package view.gui.histogram;

import model.imagefile.ReadOnlyImageFile;

public interface IHistogramSurveyor {
  void updateHistogramList(ReadOnlyImageFile currImageFile);
}
