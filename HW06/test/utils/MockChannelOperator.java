package utils;

import model.operation.opertor.colortrans.IColorTransOperator;

/**
 * A MockChannelOperator to introduce error in the test.
 */
public enum MockChannelOperator implements IColorTransOperator {
  Mock;

  @Override
  public double[][] getMatrix() {
    return new double[0][];
  }
}
