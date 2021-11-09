package model.operation.function;

import java.awt.Color;

/**
 * A function interface that has a method consume a {@link Color} and convert it to a new
 * {@link Color} by the defined rules.
 */
public interface IFilterFunction {
  public Color[][] apply(Color[][] original, double[][] kernel);
}
