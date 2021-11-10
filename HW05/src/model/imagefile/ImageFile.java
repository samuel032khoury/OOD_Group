package model.imagefile;

import model.operation.IImageOperation;

/**
 * To represent an image that can apply a series of (if supported) image processing
 * operations.
 */
public interface ImageFile extends ReadOnlyImageFile {
  //TODO
  ImageFile applyOperation(IImageOperation operation);

  /**
   * Deep copy a {@link ImageFile} object with duplicated information.
   *
   * @return a copy of the image.
   */
  ImageFile copy();
}
