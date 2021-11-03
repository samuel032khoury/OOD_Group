package model.imageFile;

import java.awt.Color;

/**
 * To represent an image that can only be viewed, but cannot be modified or be copied.
 */
public interface ReadOnlyImageFile {
  /**
   * get the height of an image represented by {@link ReadOnlyImageFile}.
   *
   * @return the height of an image
   */
  int getHeight();

  /**
   * get the width of an image represented by {@link ReadOnlyImageFile}.
   *
   * @return the width of an image
   */
  int getWidth();

  /**
   * determine if the {@link ReadOnlyImageFile} object support alpha channel.
   *
   * @return true if the alpha channel is open, false if it's closed.
   */
  boolean alpha();

  /**
   * get the possible maximum value of color channel of an image represented by {@link
   * ReadOnlyImageFile}.
   *
   * @return the possible maximum value of color channel of an image
   */
  int getMaxColorVal();

  /**
   * get the {@link Color} of an image represented by {@link ReadOnlyImageFile} at a pixel
   * determined by {@code row} and {@code col}.
   *
   * @return the color at the specified pixel
   */
  Color getColorAt(int row, int col);
}
