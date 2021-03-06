package controller.utils;

import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;

/**
 * An interface for converting an {@link ImageFile} into a file and writing it into machines file
 * system.
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