package controller.utils;

import model.imageFile.ImageFile;
import model.imageFile.ReadOnlyImageFile;

/**
 * An interface for writing the ImageFile into a file.
 */
public interface IWriter {
  /**
   * convert a {@link ImageFile} to a file and write into machine's file system.
   *
   * @param img      the image file.
   * @param fileName the path to store the file.
   * @throws IllegalStateException if the file cannot to be written.
   */
  void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException;
}