package controller.utils;

/**
 * A concrete class to manage loaders.
 */
public class LoadManager extends AManager<ILoader> {
  public LoadManager() {
    availableSuffix.put("ppm", PPMLoader::new);
  }
}