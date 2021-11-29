package model.image;

import java.util.List;
import model.image.pixel.Pixel;

/**
 * This interface contains all the generic operations that pertain to the images. This interface is
 * the baseline for all possible implementations of the image model -- a state-storing, managing
 * class for the application.
 */
public interface Image {

  /**
   * Returns the parsed image data in a matrix form, where every cell represents a {@link Pixel}.
   *
   * @return a two-dimensional matrix of pixels.
   */
  List<List<Pixel>> getImageData();

  /**
   * Get the width of this picture.
   *
   * @return The width
   */
  int getWidth();

  /**
   * Get the height of this picture.
   *
   * @return The height
   */
  int getHeight();

  /**
   * Gets the pixel at the specified location.
   *
   * @param row The row of the pixel
   * @param col The column of the pixel
   * @return The pixel at the given location
   * @throws IndexOutOfBoundsException If the requested index is out of bounds for this image.
   */
  Pixel getPixel(int row, int col) throws IndexOutOfBoundsException;

  /**
   * Sets the pixel at the specified location.
   *
   * @param row The row of the pixel
   * @param col The column of the pixel
   * @param p   The pixel value to set at this location
   * @throws IndexOutOfBoundsException If the requested index is out of bounds for this image.
   */
  void setPixel(int row, int col, Pixel p) throws IndexOutOfBoundsException;

  /**
   * Creates a copy of this image.
   *
   * @return a copy of the current image
   */
  Image copy();
}
