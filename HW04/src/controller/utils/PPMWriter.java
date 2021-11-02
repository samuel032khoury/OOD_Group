package controller.utils;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.imageFile.ReadOnlyImageFile;

/**
 * A concrete class of writer, that can handle the writing of ppm file.
 */
public class PPMWriter implements IWriter {

  /**
   * writing the ImageFile into a file.
   * @param img the image file.
   * @param fileName the path to store the file.
   * @throws IllegalStateException if unable to create destination file,
   *         written process is corrupted,
   */
  @Override
  public void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException {
    BufferedWriter myWriter;
    int height = img.getHeight();
    int width = img.getWidth();
    int maxColorVal = img.getMaxColorVal();

    try {
      myWriter = new BufferedWriter(new FileWriter(fileName));
      myWriter.write("P3\n");
      myWriter.write("# ppm - RGB\n");
      myWriter.write(String.format("%d %d\n", width, height));
      myWriter.write(maxColorVal + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to create the destination file!");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color color = img.getColorAt(i, j);
        try {
          myWriter.write(color.getRed() + " ");
          myWriter.write(color.getGreen() + " ");
          myWriter.write(color.getBlue() + " ");
          if (j == width - 1) {
            myWriter.write("\n");
          }
        } catch (IOException e) {
          throw new IllegalStateException("Write was interrupted, please try again!");
        }
      }
    }

    try {
      myWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Written file cannot be saved, please try again!");
    }
  }
}
