package controller.utils;

/**
 * A concrete class to manage writers.
 */
public class WriteManager extends AManager<IWriter> {
  public WriteManager() {
    availableSuffix.put("ppm", PPMWriter::new);
  }
}