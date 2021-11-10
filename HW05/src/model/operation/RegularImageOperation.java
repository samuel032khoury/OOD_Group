package model.operation;

import java.awt.Color;
import java.util.Objects;

//TODO
public abstract class RegularImageOperation implements IImageOperation {
  protected int height;
  protected int width;

  //TODO
  @Override
  public final Color[][] apply(boolean alphaSupported, Color[][] pixels) {
    if (this.alphaRelated() && !alphaSupported) {
      throw new IllegalStateException("This is a alpha related operation while the provided image"
              + " does not have alpha channel!");
    }

    Color[][] result;
    try {
      this.height = (pixels.length);
      this.width = pixels[0].length;
      result = Objects.requireNonNull(this.process(pixels));
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Invalid Image!");
    }
    return result;
  }

  //TODO
  @Override
  public int updateMaxColorVal(int original) {
    return original;
  }

  //TODO
  @Override
  public boolean updateAlphaChannel(boolean original) {
    return original;
  }


  //TODO
  protected abstract boolean alphaRelated();

  //TODO
  protected abstract Color[][] process(Color[][] pixels);
}
