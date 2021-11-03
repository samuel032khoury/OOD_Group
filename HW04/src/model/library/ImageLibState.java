package model.library;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent a read-only Image Library that stored all the images has been loaded into the
 * program.
 */
public interface ImageLibState {
  /**
   * get the size of (the number of image loaded to) the library.
   *
   * @return the size of the library
   */
  int getLibSize();

  /**
   * get a {@link ReadOnlyImageFile} object stored in the image library by the image name.
   *
   * @param imageName the image name of the {@link ReadOnlyImageFile} being looking for.
   * @return a {@link ReadOnlyImageFile} if the search succeed, null if there isn't such an image.
   */
  ReadOnlyImageFile peek(String imageName);
}
