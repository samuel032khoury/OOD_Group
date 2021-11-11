package model.operation;

import java.awt.*;
import java.util.Objects;

/**
 * To represent an implementation of {@link IImageOperation}, with the restriction that if an {@link
 * RegularImageOperation} is alpha related, then the image that doesn't have an alpha channel should
 * not be processed by that operation. Any operations that extending {@link RegularImageOperation}
 * should not change the possible maximum Color value and the availability of the alpha channel of
 * an image as well.
 */
public abstract class RegularImageOperation implements IImageOperation {
  protected int height;
  protected int width;

  /**
   * To apply an operation with the rule  provided by the {@link #process} from concrete classes on
   * the given {@code pixels}, with the restriction that if the given image does not support alpha
   * channel, then the operation that tries to change alpha value should not be allowed applying.
   *
   * @param alphaSupported the availability of the alpha channel of an image to which the operation
   *                       applied.
   * @param pixels         a 2-D {@code Array} of {@link Color} that represents an image.
   * @return an updated 2-D {@code Array} of {@link Color} representing an image have been processed
   * by an operation with the rule provided by {@link #process} from concrete classes.
   */
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

  /**
   * To update the possible maximum Color value for an image.
   *
   * @param original the original possible maximum Color value
   * @return the original possible maximum Color value, as extending class of {@link
   * RegularImageOperation} do not change the possible maximum Color value.
   */
  @Override
  public int updateMaxColorVal(int original) {
    return original;
  }

  /**
   * To update the availability of the alpha channel of an image.
   *
   * @param original the original availability of the alpha channel of an image
   * @return the original availability of the alpha channel, as extending class of {@link
   * RegularImageOperation} do not change the availability of the alpha channel of an image.
   */
  @Override
  public boolean updateAlphaChannel(boolean original) {
    return original;
  }

  /**
   * To determine if a {@link RegularImageOperation} is an alpha related operation.
   *
   * @return true if the {@link RegularImageOperation} is alpha related
   */
  protected abstract boolean alphaRelated();

  /**
   * Perform operations on the given {@code pixels}, the operation rule depends on specific
   * implementations.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a processed 2-D {@code Array} of {@link Color} representing an image, by the operation
   * rule provided by concrete classes.
   */
  protected abstract Color[][] process(Color[][] pixels);
}
