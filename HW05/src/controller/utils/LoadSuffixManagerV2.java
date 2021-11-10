package controller.utils;

/**
 * A {@link ASuffixManager} that is able to provide whenever a .ppm file is going
 * to be loaded.
 */
public class LoadSuffixManagerV2 extends LoadSuffixManager{

  /**
   * Construct an {@link ASuffixManager} for loading with support to bmp, jpg, png, jpeg format.
   */
  public LoadSuffixManagerV2() {
    super();
    this.availableSuffix.put("bmp", UniLoader::new);
    this.availableSuffix.put("jpg", UniLoader::new);
    this.availableSuffix.put("png", UniLoader::new);
    this.availableSuffix.put("jpeg", UniLoader::new);
  }
}
