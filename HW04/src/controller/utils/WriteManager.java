package controller.utils;

/**
 * A concrete class to manage writers.
 */
public class WriteManager extends AManager<IWriter> {
  public WriteManager() {
    this.availableLoaders.put("ppm", PPMWriter::new);
  }
}