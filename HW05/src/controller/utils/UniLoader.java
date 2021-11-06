package controller.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ImageFile;

public class UniLoader implements ILoader{
  BufferedImage img;




  @Override
  public ImageFile loadFile(String fileName) throws IllegalStateException {
    try {
      img = ImageIO.read(new File("strawberry.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
