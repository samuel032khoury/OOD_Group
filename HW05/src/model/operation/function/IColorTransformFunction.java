package model.operation.function;

import java.awt.*;

/**
 * A function interface that has a method consume a {@link Color} and convert it to a new {@link
 * Color} by the defined rules provided by the {@code transformMatrix}.
 */
public interface IColorTransformFunction {
  /**
   * convert the provided {@link Color} to a new {@link Color} according to the {@code
   * transformMatrix}.
   *
   * @param c               the original color being consumed
   * @param transformMatrix the transform matrix being applied to all color channel of the
   *                        provided {@code c}.
   * @return a new {@link Color} modified according to the transform matrix.
   */
  Color apply(Color c, double[][] transformMatrix);
}
