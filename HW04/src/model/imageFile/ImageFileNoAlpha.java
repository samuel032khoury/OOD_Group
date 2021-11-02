package model.imageFile;

import java.awt.Color;


public class ImageFileNoAlpha extends AImageFile {

  public ImageFileNoAlpha(Color[][] pixels) {
    this(pixels, 255);
  }

  public ImageFileNoAlpha(Color[][] pixels, int maxColorVal) {
    super(pixels, maxColorVal);
  }
}
