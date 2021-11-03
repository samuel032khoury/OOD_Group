package model.library;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent a read-only Image Library that stored all the images has been loaded into the
 * program.
 */
public interface ImageLibState {
  int getLibSize();

  ReadOnlyImageFile peek(String imageName);
}
