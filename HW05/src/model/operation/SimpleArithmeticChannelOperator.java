package model.operation;

/**
 * To represent a set of channel operations that grayscale a {@link java.awt.Color} by performing
 * simple arithmetic using channel values and unifying all channel values with the result.
 */
public enum SimpleArithmeticChannelOperator implements IChannelOperator {
  Value(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}),
  Luma(new double[][]{{0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}}),
  Intensity(new double[][]{{0.3333, 0.3333, 0.3333},
          {0.3333, 0.3333, 0.3333}, {0.3333, 0.3333, 0.3333}});

  private final double[][] transformMatrix;

  SimpleArithmeticChannelOperator(double[][] transformMatrix) {
    OperationUtil.checkColorTransformMatrix(transformMatrix);
    this.transformMatrix = transformMatrix;
  }

  @Override
  public double[][] getMatrix() {
    return this.transformMatrix;
  }
}
