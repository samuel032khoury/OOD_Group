package model.image.operation.colortransform.linear.grayscale;

/**
 * Performs an intensity-based grayscale operation on the image. An intensity-based grayscale filter
 * sets all the channels of an RGB pixel to the rounded average value of all the channels of a
 * pixel.
 *
 * <p>Example: An RGB pixel (255, 120, 31) would become (135, 135, 135), whereas a pixel (0, 0, 1)
 * would become (0, 0, 0).
 */
public class IntensityGrayscaleOperation extends AbstractGrayscaleOperation {

  @Override
  protected double[] getGrayscaledTransform() {
    return new double[]{1.0 / 3, 1.0 / 3, 1.0 / 3}; // we take the average of the three values
  }
}