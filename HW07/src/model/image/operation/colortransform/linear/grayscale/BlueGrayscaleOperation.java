package model.image.operation.colortransform.linear.grayscale;

/**
 * Performs a blue grayscale shift on the given image.
 */
public class BlueGrayscaleOperation extends AbstractGrayscaleOperation {

  @Override
  protected double[] getGrayscaledTransform() {
    return new double[]{0, 0, 1}; // we only need the blue value
  }
}
