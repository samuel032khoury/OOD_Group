package model.operation.opertor.colortrans;

import model.operation.color.OperationUtil;

/**
 * To enumerate a set of color transform operators that grayscale a {@link java.awt.Color} by
 * performing simple arithmetic on channel values and unifying all channel values with the result.
 */
public enum SimpleArithmeticGreyscaleOperator implements IColorTransOperator {
  Value(new double[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}),
  Luma(new double[][]{{0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}}),
  Intensity(new double[][]{{0.3333, 0.3333, 0.3333},
          {0.3333, 0.3333, 0.3333}, {0.3333, 0.3333, 0.3333}});

  private final double[][] transformMatrix;

  /**
   * To construct a {@link SimpleArithmeticGreyscaleOperator} enum member with the {@code
   * double[][]} representing a color transform matrix.
   *
   * @param transformMatrix the transform matrix for the operator.
   */
  SimpleArithmeticGreyscaleOperator(double[][] transformMatrix) {
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
