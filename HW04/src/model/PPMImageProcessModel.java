package model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PPMImageProcessModel implements ImageProcessModel{
  private Map<String, BufferedImage> images;

  PPMImageProcessModel() {
    images = new HashMap<>();
  }

  @Override
  public void load(String imageName, BufferedImage image) {
    images.put(imageName, image);
  }

  @Override
  public void vertiFlip(String imageName) {

  }

  @Override
  public void horizFlip(String imageName) {

  }

  @Override
  public void brighten(String imageName) {

  }

  @Override
  public void darken(String imageName) {

  }

  @Override
  public void greyscale(String imageName) {

  }

  @Override
  public BufferedImage getImage(String imageName) throws IllegalArgumentException{
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("No such image loaded!");
    }
    return images.get(imageName);
  }
}