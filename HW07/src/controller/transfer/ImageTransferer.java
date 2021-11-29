package controller.transfer;

import java.io.IOException;
import model.image.Image;

/**
 * Interface for transferring images to and from various file sources.
 */
public interface ImageTransferer {

  /**
   * Loads an image from the given file path.
   *
   * @param filePath the file path to load from
   * @return a normalized image object from the data in the file at the specied path
   * @throws IOException If there are any read-related issue with the file (for instance, and
   *                     invalid filename or an invalid encoding)
   */

  Image load(String filePath) throws IOException;

  /**
   * Exports an image to the given file path (this method automatically determines the extension of
   * the export based on the filepath supplied).
   *
   * @param img      the image to export
   * @param filePath the file path to export to
   * @throws IOException If there are any write-related issue with the file (for instance, and
   *                     invalid filename or an invalid encoding)
   */
  void export(Image img, String filePath) throws IOException;

  /**
   * Returns the supported file types for this transferer, separated by new lines.
   *
   * @return A new line separated string to be displayed as help text.
   */
  String getFileTypes();
}
