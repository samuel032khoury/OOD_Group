package model.image.operation.colortransform.linear.grayscale;

/**
 * Performs a red grayscale shift on the given image.
 */
public class RedGrayscaleOperation extends AbstractGrayscaleOperation {

  @Override
  protected double[] getGrayscaledTransform() {
    return new double[]{1, 0, 0}; // we only need the red value
  }
}
