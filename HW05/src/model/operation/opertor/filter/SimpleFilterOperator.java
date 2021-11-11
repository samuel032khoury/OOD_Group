package model.operation.opertor.filter;

import model.operation.color.OperationUtil;

/**
 * A filter operator, that changes the pixels.
 */
public enum SimpleFilterOperator implements IFilterOperator {
  Blur(new double[][]{{1 / 16.0, 1 / 8.0, 1 / 16.0}, {1 / 8.0, 1 / 4.0, 1 / 8.0}, {1 / 16.0,
          1 / 8.0, 1 / 16.0}}),
  Sharpening(new double[][]{
          {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
          {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
          {-1 / 8.0, 1 / 4.0, 1, 1 / 4.0, -1 / 8.0},
          {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
          {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}});

  private final double[][] kernel;

  //TODO
  SimpleFilterOperator(double[][] kernel) {
    OperationUtil.checkKernel(kernel);
    this.kernel = kernel;
  }

  //TODO
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
