package model;

public interface ImageLibState {
  public int getLibSize();

  public ReadOnlyImageFile peek(String imageName);
}
