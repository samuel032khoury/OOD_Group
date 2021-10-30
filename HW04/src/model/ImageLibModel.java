package model;

public interface ImageLibModel extends ImageLibState{
  void load(String imageName, ImageFile imageFile);
  ImageFile get(String imageName);
}
