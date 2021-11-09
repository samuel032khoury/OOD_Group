package model.operation.opertor.colortrans;

import model.operation.color.OperationUtil;

public enum TiltingOperator implements IColorTransOperator {
  Sepia(new double[][]{{0.393, 0.769, 0.189},{0.349,0.686,0.168},{0.272, 0.534, 0.131}});

  private final double[][] transformMatrix;

  TiltingOperator(double[][] transformMatrix) {
    OperationUtil.checkColorTransformMatrix(transformMatrix);
    this.transformMatrix = transformMatrix;
  }

  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
