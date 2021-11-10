package model.operation.opertor.colortrans;

import model.operation.color.OperationUtil;

/**
 * To represent a set of channel operations that grayscale a {@link java.awt.Color} by selecting one
 * of the channel values and unifying all channel values with the selection.
 */
public enum SingleChannelGreyscaleOperator implements IColorTransOperator {
  Red(new double[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}}),
  Green(new double[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}}),
  Blue(new double[][]{{0, 0, 1}, {0, 0, 1}, {0, 0, 1}});

  private final double[][] transformMatrix;

  //TODO
  SingleChannelGreyscaleOperator(double[][] transformMatrix) {
    OperationUtil.checkColorTransformMatrix(transformMatrix);
    this.transformMatrix = transformMatrix;
  }

  //TODO
  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
