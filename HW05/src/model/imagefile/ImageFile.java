package model.imagefile;

import model.operation.IImageOperation;

/**
 * To represent an image file that only has RGB value stored and can apply a series of image
 * processing functionalities.
 */
public interface ImageFile extends ReadOnlyImageFile {
  ImageFile applyOperation(IImageOperation operation);

//  ImageFile copy();
}
