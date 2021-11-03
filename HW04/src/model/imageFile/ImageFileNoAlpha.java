package model.imagefile;

import model.imagefile.AImageFile;

import java.awt.Color;

/**
 * To represent a simple image file that has no alpha channel.
 */
public class ImageFileNoAlpha extends AImageFile {
  /**
   * construct a concrete class that represent an image with only RGB channel, using the provided
   * color information stored in the 2-D array.
   *
   * @param pixels the 2-D color array storing image's color information.
   */
  public ImageFileNoAlpha(Color[][] pixels) {
    this(pixels, 255);
  }

  /**
   * construct a concrete class that represent an image with only RGB channel, using the provided
   * color information stored in the 2-D array, and a specified possible maximum value for color
   * channels.
   *
   * @param pixels      the 2-D color array storing image's color information.
   * @param maxColorVal the possible maximum value for color channels
   */
  public ImageFileNoAlpha(Color[][] pixels, int maxColorVal) {
    super(pixels, maxColorVal);
  }
}
