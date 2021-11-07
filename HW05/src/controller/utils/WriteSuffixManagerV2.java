package controller.utils;

public class WriteSuffixManagerV2 extends WriteSuffixManager{
  public WriteSuffixManagerV2() {
    super();
    this.availableSuffix.put("bmp", UniWriter::new);
    this.availableSuffix.put("png", UniWriter::new);
    this.availableSuffix.put("jpg", UniWriter::new);
    this.availableSuffix.put("jpeg", UniWriter::new);

  }
}
