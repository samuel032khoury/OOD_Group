package controller.utils;

import model.imagefile.ImageFile;

/**
 * An interface for loading a file and convert it into an {@link ImageFile} object.
 */
public interface ILoader {
  /**
   * load a file from the machine's file system as a {@link ImageFile} object.
   *
   * @param fileName the pathname of the file
   * @return a ImageFile that have the format of the file.
   * @throws IllegalStateException if the file cannot be loaded.
   */
  ImageFile loadFile(String fileName) throws IllegalStateException;
}
