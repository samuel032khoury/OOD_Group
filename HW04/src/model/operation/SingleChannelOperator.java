package model.operation;


/**
 * To represent a set of channel operations that grayscale a {@link java.awt.Color} by selecting
 * one of the channel values and unifying all channel values with the selection.
 */
public enum SingleChannelOperator implements IChannelOperator {
  Red, Green, Blue
}
