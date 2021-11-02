package controller.utils;

import model.imageFile.ReadOnlyImageFile;

/**
 * An interface for writing the ImageFile into a file.
 */
public interface IWriter {
  /**
   * writing the ImageFile into a file.
   * @param img the image file.
   * @param fileName the path to store the file.
   * @throws IllegalStateException if the file cannot to be written.
   */
  void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException;
}