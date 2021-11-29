package model.image.operation.colortransform.linear.grayscale;

/**
 * Performs a luma-based grayscale operation on the image. A luma-based grayscale filter sets all
 * the channels of an RGB pixel to the weighted (rounded) sum 0.2126r + 0.7152g + 0.0722b.
 *
 * <p>Example: An RGB pixel (255, 120, 31) would become (142, 142, 142), whereas a pixel (0, 0, 1)
 * would become (0, 0, 0).
 */
public class LumaGrayscaleOperation extends AbstractGrayscaleOperation {

  @Override
  protected double[] getGrayscaledTransform() {
    return new double[]{0.2126, 0.7152, 0.0722}; // luma transformation
  }
}