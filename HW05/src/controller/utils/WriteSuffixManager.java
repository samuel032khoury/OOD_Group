package controller.utils;

/**
 * A concrete class to manage writers.
 */
public class WriteSuffixManager extends ASuffixManager<IWriter> {
  /**
   * Construct an {@link ASuffixManager} for file writing with support to ppm format.
   */
  public WriteSuffixManager() {
    availableSuffix.put("ppm", PPMWriter::new);
  }
}