package controller.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR_PRE;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE;

/**
 * An implementation of {@link ILoader}, with support to convert a conventional image file into a
 * 2-D {@code Array} of {@link Color} and use that array information (along with other meta
 * information retrieved from the image) to build a {@link ImageFile}.
 */
public class UniLoader implements ILoader {
  private final int maxColorVal;

  public UniLoader() {
    this(255);
  }

  public UniLoader(int maxColorVal) {
    this.maxColorVal = maxColorVal;
  }

  /**
   * load a file from the machine's file system as a {@link ImageFile} object.
   *
   * @param pathName the path name of the file
   * @return a ImageFile that have the format of the file.
   * @throws IllegalStateException if the file cannot be found, the file is invalid, or if the file
   *                               is broken.
   */
  @Override
  public ImageFile loadFile(String pathName) throws IllegalStateException {
    BufferedImage img;
    boolean alpha = false;

    try {
      new FileInputStream(pathName);
      img = ImageIO.read(new File(pathName));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find an image file named \"" + pathName + "\"! "
              + "Please check the name or the path of the file is accurate and try again!");
    } catch (IOException e) {
      throw new IllegalStateException("An error occurs when loading a file!");
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
        pixels[i][j] = new Color(img.getRGB(j, i), true);
      }
    }

    return new ImageFileImpl(pixels, this.maxColorVal, alpha);
  }
}