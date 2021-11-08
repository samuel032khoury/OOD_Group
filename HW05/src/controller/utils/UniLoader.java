//package controller.utils;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import model.imagefile.ImageFile;
//
//public class UniLoader implements ILoader{
//
//  @Override
//  public ImageFile loadFile(String fileName) throws IllegalStateException {
//    BufferedImage img = null;
//
//    try {
//      img = ImageIO.read(new File(fileName));
//    } catch (IOException e) {
//      throw new IllegalStateException("unable to read the image");
//    }
//
//    if (img == null) {
//      throw new IllegalStateException("unable to read the image");
//    }
//
//    int height = img.getHeight();
//    int width = img.getWidth();
//
//    Color[][] pixels = new Color[height][width];
//
//    for (int i = 0; i < height; i++) {
//      for (int j = 0; j < width; j++) {
//        pixels[i][j] = new Color(img.getRGB(i,j));
//      }
//    }
//
//    return new SomethingImage();
//  }
//}
