package model.image.operation.colortransform;

import model.image.pixel.Pixel;

/**
 * Performs a value-based grayscale operation on the image. A value-based grayscale filter sets all
 * the channels of a pixel to the maximum value of all components.
 *
 * <p>Example: An RGB pixel (255, 120, 31) would become (255, 255, 255), whereas a pixel (0, 0, 1)
 * would become (1, 1, 1).
 */
public class ValueGrayscaleOperation extends AbstractColorTranformOperation {

  @Override
  protected Pixel getNewPixel(Pixel px) {
    Pixel newPixel = px.copy();

    int maxValue = Math.max(px.getRed(), Math.max(px.getGreen(), px.getBlue()));

    newPixel.setRed(maxValue);
    newPixel.setBlue(maxValue);
    newPixel.setGreen(maxValue);

    return newPixel;
  }
}
