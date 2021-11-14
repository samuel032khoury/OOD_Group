package controller.utils;

import model.imagefile.ImageFile;

/**
 * A {@link ASuffixManager} that is able to provide {@link PPMWriter} whenever a {@link ImageFile}
 * is going to be saved as a .ppm file.
 */
public class WriteSuffixManager extends ASuffixManager<IWriter> {

  /**
   * Construct an {@link ASuffixManager} for file writing with support to ppm format.
   */
  public WriteSuffixManager() {
    availableSuffix.put("ppm", PPMWriter::new);
  }
}