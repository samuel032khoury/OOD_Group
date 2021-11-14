package model.imagefile;

import model.operation.IImageOperation;

/**
 * To represent an image that can apply a set of (if supported) image processing operations.
 */
public interface ImageFile extends ReadOnlyImageFile {
  /**
   * To create an {@link ImageFile} that is a demanded-operation-applied version of the current
   * {@link ImageFile}.
   *
   * @param operation the {@link IImageOperation} that specifies the transform rules of an
   *                  operation.
   * @return a (new) updated {@link ImageFile} that applied the demanded operation.
   */
  ImageFile applyOperation(IImageOperation operation);

  /**
   * Deep copy a {@link ImageFile} object with duplicated information.
   *
   * @return a copy of the image.
   */
  ImageFile copy();
}
