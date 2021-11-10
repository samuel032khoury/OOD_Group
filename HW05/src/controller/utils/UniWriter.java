package controller.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * A concrete class of IWriter, with support to load most of the file formats.
 */
public class UniWriter implements IWriter{

  private final boolean alpha;

  /**
   * The constructor of a UniWriter, with options to specify whether the writer is going
   * to generate an image with alpha or not.
   * @param alpha true if the image supports alpha format, false otherwise.
   */
  public UniWriter(boolean alpha) {
    this.alpha = alpha;
  }


  /**
   * convert a {@link ImageFile} to a file and write into machine's file system.
   *
   * @param img      the image file.
   * @param fileName the path to store the file.
   * @throws IllegalStateException if unable to create destination file, written process is
   *                               corrupted.
   */
  @Override
  public void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException {
    int height = img.getHeight();
    int width = img.getWidth();

    BufferedImage image;

    if (alpha) {
      image = new BufferedImage(width, height, TYPE_4BYTE_ABGR);
    } else {
      image = new BufferedImage(width, height, TYPE_INT_RGB);
    }

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
