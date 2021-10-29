package model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class PPMImageProcessModel implements ImageProcessModel{
  private Map<String, Color[][]> images;

  PPMImageProcessModel() {
    images = new HashMap<>();
  }

  @Override
  public void load(String imageName, Color[][] image) {
    images.put(imageName, image);
  }

  @Override
  public void vertiFlip(String imageName) {
    Color[][] image = findImageByName(imageName);
    for (int row = 0; row < image.length / 2; row++) {
        Color[] temp = image[row];
        image[row] = image[image.length - 1 - row];
        image[image.length - 1 - row] = temp;
    }
  }

  @Override
  public void horizFlip(String imageName) {
    Color[][] image = findImageByName(imageName);
    for (int row = 0; row < image.length; row++) {
      Color[] currRow = image[row];
      for (int col = 0; col < currRow.length / 2; col++) {
        Color temp = currRow[col];
        currRow[col] = currRow[currRow.length - 1 - col];
        currRow[currRow.length - 1 - col] = temp;
      }
    }
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
  public Color[][] getImage(String imageName) throws IllegalArgumentException{
    return findImageByName(imageName);
  }

  private Color[][] findImageByName(String imageName) throws IllegalArgumentException {
    if(!images.containsKey(imageName)) {
      throw new IllegalArgumentException("No such image loaded!");
    }
    return images.get(imageName);
  }
}