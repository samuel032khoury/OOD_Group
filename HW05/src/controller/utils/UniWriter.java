package controller.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ReadOnlyImageFile;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

public class UniWriter implements IWriter{
  @Override
  public void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException {
    int height = img.getHeight();
    int width = img.getWidth();

    BufferedImage image = new BufferedImage(width, height, TYPE_4BYTE_ABGR);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        image.setRGB(j,i, img.getColorAt(i,j).getRGB());
      }
    }

    File file = new File(fileName);
    String[] list = fileName.split("\\.");

    try {
      ImageIO.write(image, list[list.length - 1], file);
    } catch (IOException e) {
      throw new IllegalStateException("can't write");
    }

  }
}
