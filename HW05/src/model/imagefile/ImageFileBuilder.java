package model.imagefile;

import java.awt.Color;
import java.util.Arrays;

public abstract class ImageFileBuilder<T> {
  protected int maxVal;
  protected Color[][] image;

  public ImageFileBuilder() {
    this.maxVal = 255;
  }
  protected T setMaxVal(int maxVal) {
    if(maxVal < 0) {
      throw new IllegalArgumentException("Invalid maximum color value for images!");
    }
    this.maxVal = maxVal;
    return returnBuilder();
  }

  protected T importImage(Color[][] pixels) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0
            || this.twoDColorContainsNull(pixels)) {
      throw new IllegalArgumentException("Invalid Image!");
    }
    this.image  = pixels;
    return returnBuilder();
  }

  /**
   * to inspect if a 2-D Color array representing an image is broken (i.e. contains null)
   * @param pixels the 2-D Color array stores Color information for an image
   * @return true if the 2-D Color array contains {@code null}
   */
  private boolean twoDColorContainsNull(Color[][] pixels) {
    boolean nullDetected = false;
    for (Color[] pixel : pixels) {
      if (pixel == null || nullDetected) {
        return true;
      }
      nullDetected = Arrays.asList(pixel).contains(null);
    }
    return nullDetected;
  }

  protected abstract ImageFile build();
  protected abstract T returnBuilder();
}
