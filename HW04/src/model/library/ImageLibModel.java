package model.library;

import model.imagefile.ImageFile;

public interface ImageLibModel extends ImageLibState {

  void loadImage(String imageName, ImageFile imageFile);

  ImageFile get(String imageName) throws IllegalStateException;
}
