package controller.utils;

import model.imagefile.ImageFile;

/**
 * An interface for loading file into imageFile object.
 */
public interface ILoader {
  /**
   * Load a file into imageFile object.
   * @param fileName the pathname of the file
   * @return a ImageFile that have the format of the file.
   * @throws IllegalStateException if the file cannot be loaded.
   */
  ImageFile loadFile(String fileName) throws IllegalStateException;
}
