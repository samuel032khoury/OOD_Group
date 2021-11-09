package model.operation.opertor.colortrans;

import model.operation.function.IColorTransformFunction;

/**
 * Interfaces for enums, each enum member should be the name of a channel operation and has its
 * corresponding {@link IColorTransformFunction} implemented.
 */
public interface IColorTransOperator {
  double[][] getMatrix();
}
