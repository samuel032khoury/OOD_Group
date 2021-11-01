package model.library;

import java.util.HashMap;
import java.util.Map;

import model.imageFile.ImageFile;
import model.imageFile.ReadOnlyImageFile;

public class ImageLibModelImpl implements ImageLibModel {
  private Map<String, ImageFile> imageLib;

  public ImageLibModelImpl() {
    this.imageLib = new HashMap<>();
  }

  @Override
  public void loadImage(String imageName, ImageFile imageFile) {
    imageLib.put(imageName, imageFile);
  }

  @Override
  public ImageFile get(String imageName) throws IllegalStateException {
    if (!this.imageLib.containsKey(imageName)) {
      throw new IllegalStateException("No such an image can be found!");
    }
    return imageLib.get(imageName).copyImage();
  }

  @Override
  public int getLibSize() {
    return imageLib.size();
  }

  @Override
  public ReadOnlyImageFile peek(String imageName) {
    return imageLib.get(imageName);
  }
}
