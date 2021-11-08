package model.operation;

public enum ColorTransOperator implements IChannelOperator{
  Sepia(new double[][]{{0.393, 0.769, 0.189},{0.349,0.686,0.168},{0.272, 0.534, 0.131}});

  private final double[][] transformMatrix;

  ColorTransOperator(double[][] transformMatrix) {
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3
            || transformMatrix[1].length != 3 || transformMatrix[2].length != 3) {
      throw new IllegalArgumentException("The Provided Color transformation matrix is invalid!");
    }
    this.transformMatrix = transformMatrix;
  }

  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
