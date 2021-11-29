package model.image.operation.colortransform.linear.grayscale;

import model.image.operation.colortransform.linear.AbstractLinearColorTransformOperation;

/**
 * A superclass for all operations that pertain to grayscale-based commands.
 *
 * <p>A grayscale-based filter sets all the channels in a pixel to the same value, which cane be
 * received in multiple ways. This transformation is applied to every single pixel in an image.
 *
 * <p>Currently supports the following operations:
 * <ul>
 *   <li>red-component</li>
 *   <li>green-component</li>
 *   <li>blue-component</li>
 *   <li>luma-component</li>
 *   <li>value-component</li>
 *   <li>intensity-component</li>
 * </ul>
 */
public abstract class AbstractGrayscaleOperation extends AbstractLinearColorTransformOperation {

  @Override
  protected double[][] getTransformation() {
    return new double[][]{
        getGrayscaledTransform(),
        getGrayscaledTransform(),
        getGrayscaledTransform()
    };
  }

  /**
   * Gets the intended grayscale transformation vector for this grayscale operation. This
   * transformation vector would be applied on every pixel and then set to that pixel's channels.
   * Must be implemented in subclasses.
   */
  protected abstract double[] getGrayscaledTransform();
}
