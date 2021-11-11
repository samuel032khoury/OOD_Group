package model.operation;

import java.awt.Color;

import model.imagefile.ImageFile;

/**
 * To represent an interfaces for operations that can be applied to a {@link ImageFile}.
 */
public interface IImageOperation {

  /**
   * Create a new 2-D {@code Array} of {@link Color} that applies an operation with on the provided
   * {@code pixels}. The operation depends on specific implementations.
   *
   * @param alphaSupported the availability of the alpha channel of an image to which the operation
   *                       applied.
   * @param pixels         a 2-D {@code Array} of {@link Color} that represents an image.
   * @return an updated 2-D {@code Array} of {@link Color} representing an image have been processed
   *         by an operation. The operation depends on specific implementations.
   */
  Color[][] apply(boolean alphaSupported, Color[][] pixels);

  /**
   * To update the possible maximum Color value for an image.
   *
   * @param original the original possible maximum Color value
   * @return the updated possible maximum Color value
   */
  int updateMaxColorVal(int original);

  /**
   * To update the availability of the alpha channel of an image.
   *
   * @param original the original availability of the alpha channel of an image
   * @return the updated availability of the alpha channel of an image
   */
  boolean updateAlphaChannel(boolean original);
}
