package controller.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR_PRE;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE;

/**
 * A concrete class of ILoader, with support to load most of the file formats.
 */
public class UniLoader implements ILoader{

  /**
   * load a file from the machine's file system as a {@link ImageFile} object.
   *
   * @param fileName the pathname of the file
   * @return a ImageFile that have the format of the file.
   * @throws IllegalStateException if the file cannot be found, the file is invalid, or if the file
   *                               is broken.
   */
  @Override
  public ImageFile loadFile(String fileName) throws IllegalStateException {
    BufferedImage img;
    boolean alpha = false;

    try {
      img = ImageIO.read(new File(fileName));
    } catch (IOException e) {
      throw new IllegalStateException("unable to read the image");
    }

    if (img == null) {
      throw new IllegalStateException("unable to read the image");
    }

    int type = img.getType();
    if (type == TYPE_INT_ARGB || type == TYPE_INT_ARGB_PRE || type == TYPE_4BYTE_ABGR
            || type == TYPE_4BYTE_ABGR_PRE) {
      alpha = true;
    }

    int height = img.getHeight();
    int width = img.getWidth();

    Color[][] pixels = new Color[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j] = new Color(img.getRGB(j,i));
      }
    }

    // TODO: explain the 255
    return new ImageFileImpl(pixels, 255, alpha);
  }
}
