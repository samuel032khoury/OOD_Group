package model.operation.opertor.filter;

import model.operation.color.OperationUtil;

/**
 * To represent a set of channel operations that transform a array of {@link java.awt.Color}
 * by performing simple arithmetic using pixel values and
 * unifying all pixel values with the result.
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
   * Construct a SimpleFilterOperator.
   * @param kernel the matrix kernel to initialize.
   */
  SimpleFilterOperator(double[][] kernel) {
    OperationUtil.checkKernel(kernel);
    this.kernel = kernel;
  }

  /**
   * Return the filter kernel of the operator.
   * @return filter kernel of the operator.
   */
  @Override
  public double[][] getKernel() {
    return this.kernel;
  }
}
