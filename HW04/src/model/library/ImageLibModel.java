package model.library;

import model.imagefile.ImageFile;

/**
 * To represent an Image Library that stored all the images has been loaded into the program.
 */
public interface ImageLibModel extends ImageLibState {

  void loadImage(String imageName, ImageFile imageFile);

  ImageFile get(String imageName) throws IllegalStateException;
}
