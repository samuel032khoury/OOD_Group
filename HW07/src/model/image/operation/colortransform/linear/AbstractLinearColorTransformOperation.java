package model.image.operation.colortransform.linear;

import model.image.operation.colortransform.AbstractColorTranformOperation;
import model.image.pixel.Pixel;

/**
 * The parent class for all linear transformations that can be performed on images.
 */
public abstract class AbstractLinearColorTransformOperation extends AbstractColorTranformOperation {

  @Override
  protected Pixel getNewPixel(Pixel px) {
    Pixel newPixel = px.copy();

    double[][] transform = getTransformation();
    int[] pxColors = new int[]{
        px.getRed(), px.getGreen(), px.getBlue()
    };

    newPixel.setRed(multiplyPixelByTransformRow(pxColors, transform[0]));
    newPixel.setGreen(multiplyPixelByTransformRow(pxColors, transform[1]));
    newPixel.setBlue(multiplyPixelByTransformRow(pxColors, transform[2]));

    return newPixel;
  }

  /**
   * Takes the product of two 3x1 vectors, the vector of a pixel's colors (int) and a transformation
   * vector.
   *
   * @param pixelVector     the vector of pixel colors
   * @param transformVector the transformation vector for the pixel
   * @return the rounded value of the dot product of two vectors
   */
  private int multiplyPixelByTransformRow(int[] pixelVector, double[] transformVector) {
    return (int) Math.round(
        pixelVector[0] * transformVector[0] + pixelVector[1] * transformVector[1]
            + pixelVector[2] * transformVector[2]);
  }

  /**
   * Gets a 3x3 transformation matrix to apply on a given pixel.
   *
   * @return the 3x3 transformation matrix for this color transformation
   */
  protected abstract double[][] getTransformation();
}
