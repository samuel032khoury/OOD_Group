package controller.utils;

public class WriteSuffixManagerV2 extends WriteSuffixManager{
  public WriteSuffixManagerV2() {
    super();
    this.availableSuffix.put("bmp", () -> new UniWriter(true));
    this.availableSuffix.put("png", () -> new UniWriter(true));
    this.availableSuffix.put("jpg", () -> new UniWriter(false));
    this.availableSuffix.put("jpeg", () -> new UniWriter(false));

  }
}
