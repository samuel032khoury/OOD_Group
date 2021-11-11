package controller.utils;

/**
 * A {@link ASuffixManager} that is able to provide {@link UniWriter} for various major image format
 * saving given the demanded file extension.
 */
public class WriteSuffixManagerV2 extends WriteSuffixManager {

  /**
   * Construct an {@link ASuffixManager} for file writing with support to bmp, jpg, png, jpeg
   * format.
   */
  public WriteSuffixManagerV2() {
    super();
    this.availableSuffix.put("bmp", () -> new UniWriter(false));
    this.availableSuffix.put("png", () -> new UniWriter(true));
    this.availableSuffix.put("jpg", () -> new UniWriter(false));
    this.availableSuffix.put("jpeg", () -> new UniWriter(false));
  }
}
