package model.imagefile;

import java.awt.Color;

public interface ReadOnlyImageFile {

  int getHeight();

  int getWidth();

  boolean alpha();

  int getMaxColorVal();

  Color getColorAt(int row, int col);
}
