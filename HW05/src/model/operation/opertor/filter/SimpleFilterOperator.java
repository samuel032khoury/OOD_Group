package model.operation.opertor.filter;

import model.operation.color.FilterUtil;

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

  SimpleFilterOperator(double[][] kernel) {
    FilterUtil.checkKernel(kernel);
    this.kernel = kernel;
  }

  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
