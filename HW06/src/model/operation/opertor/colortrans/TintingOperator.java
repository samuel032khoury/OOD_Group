package model.operation.opertor.colortrans;

import model.operation.OperationUtil;

/**
 * To enumerate a set of channel operators that can tint a {@link java.awt.Color} by performing
 * color tinting transform matrix.
 */
public enum TintingOperator implements IColorTransOperator {
  Sepia(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});

  private final double[][] transformMatrix;

  /**
   * To construct a {@link TintingOperator} enum member with the {@code double[][]} representing a
   * color transform matrix.
   *
   * @param transformMatrix the transform matrix for the operator.
   */
  TintingOperator(double[][] transformMatrix) {
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
