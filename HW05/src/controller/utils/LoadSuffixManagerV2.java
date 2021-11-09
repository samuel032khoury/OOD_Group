package controller.utils;
/**
 * A concrete class to manage loaders.
 * The new version supports loading bmp, jpg, png, jpeg format.
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
