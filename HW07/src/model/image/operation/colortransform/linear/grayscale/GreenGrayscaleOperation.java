package model.image.operation.colortransform.linear.grayscale;

/**
 * Performs a green grayscale shift on the given image.
 */
public class GreenGrayscaleOperation extends AbstractGrayscaleOperation {

  @Override
  protected double[] getGrayscaledTransform() {
    return new double[]{0, 1, 0}; // we only need the green value
  }
}