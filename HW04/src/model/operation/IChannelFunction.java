package model.operation;

import java.awt.Color;

/**
 * A function interface that has a method consume a {@link Color} and convert it to a new
 * {@link Color} by the defined rules.
 */
public interface IChannelFunction {
  Color apply(Color c);
}
