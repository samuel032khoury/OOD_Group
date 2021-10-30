package model.Legacy;

import java.awt.Color;

import model.PixelChannel;

public interface ImageProcessModel {
  public void load(String imageName, Color[][] image);
  public void vertiFlip(String imageName) throws IllegalArgumentException;
  public void horizFlip(String imageName) throws IllegalArgumentException;
  public void brighten(String imageName, int value);
  public void darken(String imageName, int value);
  public void greyscale(String imageName, PixelChannel channel);
  public Color[][] getImage(String imageName) throws IllegalArgumentException;
}
