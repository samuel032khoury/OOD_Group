package model;

public interface ImageLibState {
  int getLibSize();

  ReadOnlyImageFile peek(String imageName);
}
