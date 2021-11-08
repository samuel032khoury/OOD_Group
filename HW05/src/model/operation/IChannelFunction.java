package model.operation;

import java.awt.Color;

/**
 * A function interface that has a method consume a {@link Color} and convert it to a new
 * {@link Color} by the defined rules.
 */
public interface IChannelFunction {
  /**
   * convert the provided {@link Color} to a new {@link Color} by the specific implemented rules.
   * @param c the original color being consumed
   * @return a new {@link Color} modified by the specific implemented rules.
   */
  Color apply(Color c, double[][] transformMatrix);
}
