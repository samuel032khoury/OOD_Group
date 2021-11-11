package model.operation.opertor.filter;

/**
 * A filter operator, that changes the pixels. The matrix have a center, that is the place to change
 * the value, each time to apply a operation, the corresponding color pixels will be multiplied by
 * the weight in the matrix, and add to find the center pixels.
 */
public interface IFilterOperator {

  /**
   * Return the filter kernel of the operator.
   * @return filter kernel of the operator.
   */
  double[][] getKernel();
}
