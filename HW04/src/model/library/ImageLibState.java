package model.library;

import model.imagefile.ReadOnlyImageFile;

public interface ImageLibState {
  int getLibSize();

  ReadOnlyImageFile peek(String imageName);
}
