package model;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageProcessModel {
  public void load(String imageName, BufferedImage image);
  public void vertiFlip(String imageName);
  public void horizFlip(String imageName);
  public void brighten(String imageName);
  public void darken(String imageName);
  public void greyscale(String imageName);
  public BufferedImage getImage(String imageName) throws IllegalArgumentException;
}
