package model.operation.opertor.colortrans;

/**
 * Interface for enums, each enum member should represent a channel operation name and must have a
 * {@code double[][]} transform matrix that contains the rule for transforming color values.
 */
public interface IColorTransOperator {

  /**
   * Get the transform matrix of an enum member.
   *
   * @return a {@code double[][]} representing the color transform matrix of the enum member.
   */
  double[][] getMatrix();
}