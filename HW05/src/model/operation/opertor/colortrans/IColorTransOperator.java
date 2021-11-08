package model.operation.opertor.colortrans;

import model.operation.function.IChannelFunction;

/**
 * Interfaces for enums, each enum member should be the name of a channel operation and has its
 * corresponding {@link IChannelFunction} implemented.
 */
public interface IColorTransOperator {
  double[][] getMatrix();
}
