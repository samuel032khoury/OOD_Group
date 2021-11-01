package controller.utils;

public class WriteManager extends AManager<IWriter> {
  public WriteManager() {
    this.availableLoaders.put("ppm", PPMWriter::new);
  }
}