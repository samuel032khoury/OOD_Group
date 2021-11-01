package model.imageFile;

import java.awt.Color;

public interface ReadOnlyImageFile {
  int getHeight();
  int getWidth();
  boolean alpha();
  int getMaxColorVal();
  Color getColorAt(int x, int y);
}
