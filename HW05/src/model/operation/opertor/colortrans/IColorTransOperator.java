package model.operation.opertor.colortrans;

import model.operation.function.IColorTransformFunction;

/**
 * Interfaces for enums, each enum member should be the name of a channel operation and has its
 * corresponding {@link IColorTransformFunction} implemented.
 * Each enum contains a matrix, the matrix contains the information about how to
 * transfrom a color pixels.
 */
public interface IColorTransOperator {

  //TODO
  double[][] getMatrix();
}
