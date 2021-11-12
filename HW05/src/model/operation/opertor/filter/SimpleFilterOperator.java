package model.operation.opertor.filter;

import model.operation.OperationUtil;

/**
 * To enumerate a set of filtering operators that can give various special effects to an image.
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

  /**
   * To construct a {@link SimpleFilterOperator} enum member with the {@code double[][]}
   * representing a filtering kernel.
   *
   * @param kernel the kernel of the filter.
   */
  SimpleFilterOperator(double[][] kernel) {
    OperationUtil.checkKernel(kernel);
    this.kernel = kernel;
  }

  /**
   * Return the filter kernel of the operator.
   *
   * @return filter kernel of the operator.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
