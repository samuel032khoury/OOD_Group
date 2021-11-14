package controller.utils;

/**
 * A {@link ASuffixManager} that is able to provide {@link PPMLoader} whenever a .ppm file is going
 * to be loaded.
 */
public class LoadSuffixManager extends ASuffixManager<ILoader> {

  /**
   * Construct an {@link ASuffixManager} for loading with support to ppm format.
   */
  public LoadSuffixManager() {
    availableSuffix.put("ppm", PPMLoader::new);
  }
}