package controller.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * An implementation of {@link IWriter}, with support to convert a 2-D {@code Array} of {@link
 * Color}, and other meta information of an image, stored in a {@link ReadOnlyImageFile} object,
 * into an image file in a format corresponding to the provided extension.
 */
public class UniWriter implements IWriter {

  private final boolean alpha;

  /**
   * The constructor of a UniWriter, with options to specify whether the writer is going to generate
   * an image with alpha or not.
   *
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
    if (img == null) {
      throw new IllegalArgumentException("ImageFile does not exist!");
    }
    if (fileName == null) {
      throw new IllegalArgumentException("File name is unspecified!");
    }

    BufferedImage image;
    int height = img.getHeight();
    int width = img.getWidth();


    if (alpha) {
      image = new BufferedImage(width, height, TYPE_4BYTE_ABGR);
    } else {
      image = new BufferedImage(width, height, TYPE_INT_RGB);
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        image.setRGB(j, i, img.getColorAt(i, j).getRGB());
      }
    }

    File file = new File(fileName);

    if (!file.getAbsoluteFile().getParentFile().canWrite()) {
      throw new IllegalStateException("Unable to create the destination file!");
    }

    String[] list = fileName.split("\\.");

    String format = list[list.length - 1].toUpperCase();

    try {
      ImageIO.write(image, format, file);
    } catch (IOException e) {
      throw new IllegalStateException("An error occurs when trying to save the file!");
    }
  }
}
