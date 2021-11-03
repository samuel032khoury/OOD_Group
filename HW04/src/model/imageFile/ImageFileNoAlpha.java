package model.imagefile;

import java.awt.Color;

/**
 * To represent a simple image file that has no alpha channel.
 */
public class ImageFileNoAlpha extends AImageFile {

  public ImageFileNoAlpha(Color[][] pixels) {
    this(pixels, 255);
  }

  public ImageFileNoAlpha(Color[][] pixels, int maxColorVal) {
    super(pixels, maxColorVal);
  }
}
