package model.imagefile;

import java.awt.Color;

/**
 * To represent an image that can only be viewed, but cannot be modified or be copied.
 */
public interface ReadOnlyImageFile {

  int getHeight();

  int getWidth();

  boolean alpha();

  int getMaxColorVal();

  Color getColorAt(int row, int col);
}
