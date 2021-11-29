package model.image.operation;

import model.image.Image;

/**
 * Describes the public methods for all image operations. Every image operation must not modify the
 * original image, instead returning a modified copy.
 */
public interface ImageOperation {

  /**
   * Applies this operation on the supplied image and returns the new, modified copy.
   *
   * @param img the image to perform the operation on
   * @return the new, processed image
   */
  Image apply(Image img);
}
