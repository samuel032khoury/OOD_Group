package model.imageoperation;

import java.awt.*;
import java.util.Objects;

public abstract class RegularImageOperation implements IImageOperation{
  protected int height;
  protected int width;

  @Override
  public final Color[][] apply(boolean alphaSupported, Color[][] pixels) {
    if(this.alphaRelated() && !alphaSupported) {
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

  @Override
  public int updateMaxColorVal(int original){
    return original;
  }

  @Override
  public boolean updateAlphaChannel(boolean original){
    return original;
  }


  protected abstract boolean alphaRelated();
  protected abstract Color[][] process(Color[][] pixels);
}
