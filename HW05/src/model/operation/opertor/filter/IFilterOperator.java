package model.operation.opertor.filter;

/**
 * Interface for enums, each enum member should represent a filter operation name and must have a
 * {@code double[][]} kernel that contains the rule for filtering and image.
 */
public interface IFilterOperator {

  /**
   * Get the filter kernel of an enum member.
   *
   * @return a {@code double[][]} representing the filter kernel of the enum member.
   */
  double[][] getKernel();
}
