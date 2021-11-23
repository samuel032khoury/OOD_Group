package model.operation;

/**
 * A Utility class that provides common functions for performing operations.
 */
public class OperationUtil {

  /**
   * Check if the color transform matrix provided is a valid one, meaning it's a 3 x 3 matrix.
   *
   * @param transformMatrix the matrix to check.
   * @throws IllegalArgumentException if the color transform matrix is not 3*3
   */
  public static void checkColorTransformMatrix(double[][] transformMatrix)
          throws IllegalArgumentException {
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3
            || transformMatrix[1].length != 3 || transformMatrix[2].length != 3) {
      throw new IllegalArgumentException("The Provided Color transformation matrix is invalid!");
    }
  }

  /**
   * Check if the filter kernel matrix provided is a valid one.
   *
   * @param kernel the matrix to check.
   * @throws IllegalArgumentException filter kernel matrix is not a rectangular matrix with odd rows
   *                                  and columns.
   */
  public static void checkKernel(double[][] kernel) throws IllegalArgumentException {
    boolean valid = false;

    if (kernel.length % 2 == 1) {
      valid = true;
      int width = kernel[0].length;
      if (width % 2 == 0) {
        valid = false;
      } else {
        for (double[] rows : kernel) {
          if (rows.length != width) {
            valid = false;
            break;
          }
        }
      }
    }

    if (!valid) {
      throw new IllegalArgumentException("Illegal kernel size!");
    }
  }

  /**
   * To bound the value of color components between 0-255.
   *
   * @param rgb a sequence of value of color components, in an order of rgb(alpha, if provided).
   * @return a list of valid (bounded between 0-255) values for color components.
   */
  public static int[] produceValidColorValue(int... rgb) {
    int[] valid = new int[rgb.length];
    for (int i = 0; i < rgb.length; i++) {
      valid[i] = Math.max(0, Math.min(255, rgb[i]));
    }
    return valid;
  }
}
