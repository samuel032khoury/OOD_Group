package model.operation.function;

import java.awt.Color;

/**
 * A function interface that has a method consume an image represented by a 2-D {@code Arrys} of
 * {@link Color} and apply the given {@code kernel} on it to make a filtered image.
 */
public interface IFilterFunction {
  //TODO
  Color[][] apply(Color[][] original, double[][] kernel);
}
