package controller.utils;

/**
 * A concrete class to manage loaders.
 */
public class LoadManager extends AManager<ILoader> {
  public LoadManager() {
    availableLoaders.put("ppm", PPMLoader::new);
  }
}