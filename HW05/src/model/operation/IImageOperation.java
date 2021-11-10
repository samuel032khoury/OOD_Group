package model.operation;

import java.awt.Color;

//TODO
public interface IImageOperation {

  //TODO
  Color[][] apply(boolean alphaSupported, Color[][] pixels);

  //TODO
  int updateMaxColorVal(int original);

  //TODO
  boolean updateAlphaChannel(boolean original);
}
