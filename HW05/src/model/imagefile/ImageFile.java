package model.imagefile;

import model.operation.IImageOperation;

/**
 * To represent an image file that only has RGB value stored and can apply a series of image
 * processing functionalities.
 */
public interface ImageFile extends ReadOnlyImageFile {
  ImageFile applyOperation(IImageOperation operation);

   /**
   * Deep copy a {@link ImageFile} object with duplicated information.
   * @return a copy of the image.
   */
  ImageFile copy();
}
