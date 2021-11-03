package model.operation;

/**
 * To represent a set of channel operations that grayscale a {@link java.awt.Color}
 *  by performing simple arithmetic using channel values and unifying all channel values with the
 *  result.
 */
public enum SimpleArithmeticChannelOperator implements IChannelOperator {
  Value, Luma, Intensity
}
