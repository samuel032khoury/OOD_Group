package model.image.operation.colortransform;

import model.image.pixel.Pixel;

/**
 * Performs a brighten operation on a given image. A brighten operation brightens or darkens a given
 * image by shifting all the channels of all pixels in an image by a specified amount.
 */
public class BrightenOperation extends AbstractColorTranformOperation {

  private final int brightenFactor;

  /**
   * Creates a new brighten operation instance.
   *
   * @param brightenFactor the factor by which to brighten the supplied image
   */
  public BrightenOperation(int brightenFactor) {
    this.brightenFactor = brightenFactor;
  }

  @Override
  protected Pixel getNewPixel(Pixel px) {
    Pixel newPixel = px.copy();

    newPixel.setBlue(px.getBlue() + brightenFactor);
    newPixel.setRed(px.getRed() + brightenFactor);
    newPixel.setGreen(px.getGreen() + brightenFactor);

    return newPixel;
  }
}
