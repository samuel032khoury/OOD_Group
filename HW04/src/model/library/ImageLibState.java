package model.library;

import model.imageFile.ReadOnlyImageFile;

public interface ImageLibState {
  int getLibSize();
  ReadOnlyImageFile peek(String imageName);
}
