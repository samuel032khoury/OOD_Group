package model.operation.color;

import java.awt.*;

import model.imagefile.ImageFile;

public class OperationUtil {

  public static void checkColorTransformMatrix(double[][] transformMatrix) throws IllegalArgumentException {
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3
            || transformMatrix[1].length != 3 || transformMatrix[2].length != 3) {
      throw new IllegalArgumentException("The Provided Color transformation matrix is invalid!");
    }
  }
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

  public static Color transform(Color c, double[][] transformMatrix) {
    int[] result = new int[3];
    int red = c.getRed();
    int blue = c.getBlue();
    int green = c.getGreen();
    result[0] = (int) (red * transformMatrix[0][0]
                    + green * transformMatrix[0][1] + blue * transformMatrix[0][2]);
    result[1] = (int) (red * transformMatrix[1][0]
                    + green * transformMatrix[1][1] + blue * transformMatrix[1][2]);
    result[2] = (int) (red * transformMatrix[2][0]
                    + green * transformMatrix[2][1] + blue * transformMatrix[2][2]);
    result = giveValidColorValue(result);
    return new Color(result[0], result[1], result[2]);
  }

  public static int[] giveValidColorValue(int... rgb) {
    for(int i = 0; i < 3; i ++) {
      rgb[i] = Math.max(1, Math.min(255, rgb[i]));
    }
    return rgb;
  }
}
