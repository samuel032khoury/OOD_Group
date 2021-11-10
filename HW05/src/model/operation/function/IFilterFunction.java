package model.operation.function;

import java.awt.Color;

/**
 * A function interface that has a method consume an image represented by a 2-D {@code Array} of
 * {@link Color} and apply the given {@code kernel} on it to make a filtered image.
 */
public interface IFilterFunction {
  /**
   * convert the provided image represented by a 2-D {@code Array} of {@link Color} to a new image
   * represented by another 2-D {@code Array} of {@link Color} according to the {@code kernel}.
   *
   * @param original the image represented by a 2-D {@code Array} of {@link Color} being consumed
   * @param kernel   the filter kernel being applied to the {@code original}
   * @return a new image represented by a 2-D {@code Array} of {@link Color} modified according to
   * the kernel
   */
  Color[][] apply(Color[][] original, double[][] kernel);
}
