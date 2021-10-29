package model;

import java.awt.Color;
import java.io.File;

public interface ImageProcessModel {
  public void load(String imageName, Color[][] image);
  public void vertiFlip(String imageName) throws IllegalArgumentException;
  public void horizFlip(String imageName) throws IllegalArgumentException;
  public void brighten(String imageName);
  public void darken(String imageName);
  public void greyscale(String imageName);
  public Color[][] getImage(String imageName) throws IllegalArgumentException;
}
