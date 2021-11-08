package controller.utils;

/**
 * A concrete class to manage writers.
 * The new version supports writing bmp, jpg, png, jpeg format.
 */
public class WriteSuffixManagerV2 extends WriteSuffixManager{
  /**
   * Construct an {@link ASuffixManager} for file writing with support to bmp, jpg, png, jpeg format.
   */
  public WriteSuffixManagerV2() {
    super();
    this.availableSuffix.put("bmp", () -> new UniWriter(true));
    this.availableSuffix.put("png", () -> new UniWriter(true));
    this.availableSuffix.put("jpg", () -> new UniWriter(false));
    this.availableSuffix.put("jpeg", () -> new UniWriter(false));

  }
}
