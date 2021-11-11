package model.operation.opertor.colortrans;

import model.operation.color.OperationUtil;

/**
 * To enumerate a set of channel operators that can grayscale a {@link java.awt.Color} by selecting
 * one of the channel values and unifying all channel values with that value.
 */
public enum SingleChannelGreyscaleOperator implements IColorTransOperator {
  Red(new double[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}}),
  Green(new double[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}}),
  Blue(new double[][]{{0, 0, 1}, {0, 0, 1}, {0, 0, 1}});

  private final double[][] transformMatrix;

  /**
   * To construct a {@link SingleChannelGreyscaleOperator} enum member with the {@code double[][]}
   * representing a color transform matrix.
   *
   * @param transformMatrix the transform matrix for the operator.
   */
  SingleChannelGreyscaleOperator(double[][] transformMatrix) {
    OperationUtil.checkColorTransformMatrix(transformMatrix);
    this.transformMatrix = transformMatrix;
  }

  /**
   * Get the transform matrix of an enum member.
   *
   * @return a {@code double[][]} representing the color transform matrix of the enum member.
   */
  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
