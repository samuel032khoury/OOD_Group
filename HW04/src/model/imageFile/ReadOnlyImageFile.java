package model.imageFile;

import java.awt.Color;

public interface ReadOnlyImageFile {
  int getHeight();
  int getWidth();
  Color getColorAt(int x, int y);
}
