package model;

import java.util.HashMap;
import java.util.Map;

public class ImageLibModelImpl implements ImageLibModel{
  private Map<String, ImageFile> imageLib;

  public ImageLibModelImpl() {
    this.imageLib = new HashMap<>();
  }

  @Override
  public void load(String imageName, ImageFile imageFile) {
    imageLib.put(imageName,imageFile);
  }

  @Override
  public int getLibSize() {
    return imageLib.size();
  }

  @Override
  public ReadOnlyImageFile peek(String imageName) {
    return imageLib.get(imageName).copy();
  }
}
