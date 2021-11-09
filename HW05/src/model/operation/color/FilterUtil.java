package model.operation.color;

public class FilterUtil {
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
          }
        }
      }
    }

    if (!valid) {
      throw new IllegalArgumentException("Illegal kernel size!");
    }
  }
}
