package model.imageFile;

import java.awt.Color;

import model.imageFile.AImageFile;

public class ImageFileNoAlpha extends AImageFile {
  public ImageFileNoAlpha(Color[][] pixels) {
    super(pixels);
    super.alphaChannel = false;
  }
}
