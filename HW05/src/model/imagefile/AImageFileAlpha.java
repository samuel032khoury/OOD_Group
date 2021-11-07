package model.imagefile;

import java.awt.Color;

public class AImageFileAlpha extends AImageFilePro implements ImageFileAlpha {
  /**
   * To construct an image file that using a 2-D Color array present an image.
   *
   * @param pixels      the 2-D Color array that stores the data information for an image
   * @param maxColorVal the possible maximum value for image's color channel
   */
  public AImageFileAlpha(Color[][] pixels, int maxColorVal) {
    super(pixels, maxColorVal);
  }

  /**
   * generate an identical copy of the image to which this method applies.
   *
   * @return an identical {@link ImageFile}.
   */
  @Override
  public ImageFileAlpha copyImage() {
    return new AImageFileAlpha(super.deepCopyPixels(super.pixels));
  }

  @Override
  public boolean alpha() {
    return true;
  }
}
