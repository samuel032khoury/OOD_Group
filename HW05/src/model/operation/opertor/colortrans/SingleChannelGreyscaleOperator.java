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

  /**
   * Construct a GreyscaleOperator.
   * @param transformMatrix the matrix to initallize.
   */
  SingleChannelGreyscaleOperator(double[][] transformMatrix) {
    OperationUtil.checkColorTransformMatrix(transformMatrix);
    this.transformMatrix = transformMatrix;
  }

  /**
   * Return the color transform matrix of the operator.
   * @return color transform matrix of the operator.
   */
  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
