package model.operation.opertor.colortrans;

import model.operation.color.OperationUtil;

/**
 * An operator that did the tiling transfer, with the same matrix in before.
 */
public enum TiltingOperator implements IColorTransOperator {
  Sepia(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});

  private final double[][] transformMatrix;

  /**
   * Construct a TiltingOperator.
   * @param transformMatrix the matrix to initialize.
   */
  TiltingOperator(double[][] transformMatrix) {
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
