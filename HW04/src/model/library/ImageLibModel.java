package model.library;

import model.imageFile.ImageFile;

public interface ImageLibModel extends ImageLibState {
  void load(String imageName, ImageFile imageFile);
  ImageFile get(String imageName) throws IllegalStateException;
}
