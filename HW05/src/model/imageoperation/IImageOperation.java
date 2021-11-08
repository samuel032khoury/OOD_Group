package model.imageoperation;

import java.awt.Color;

public interface IImageOperation {
  Color[][] apply(boolean alphaSupported, Color[][] pixels);

  int updateMaxColorVal(int original);
  boolean updateAlphaChannel(boolean original);
}
