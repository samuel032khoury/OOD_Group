package controller.utils;

/**
 * A concrete class to manage loaders.
 */
public class LoadSuffixManager extends ASuffixManager<ILoader> {
  /**
   * Construct an {@link ASuffixManager} for loading with support to ppm format.
   */
  public LoadSuffixManager() {
    availableSuffix.put("ppm", PPMLoader::new);
  }
}