package model.image.operation.geometric;

import model.image.operation.AbstractOperation;
import model.image.pixel.Pixel;

/**
 * The abstract extensible class to perform geometric image operations on images. A geometric
 * operation is an operation that does not modify the color channels of the pixels in the image, but
 * rather the placement of those pixels. Currently supports a horizontal flip and a vertical flip.
 */
public abstract class AbstractGeometricOperation extends AbstractOperation {

  @Override
  protected Pixel getNewPixel(Pixel px) {
    // since this is a geometric transformation, the pixels are not actually transformed
    return px.copy();
  }
}
