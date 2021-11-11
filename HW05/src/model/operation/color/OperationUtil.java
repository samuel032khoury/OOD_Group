package model.operation.color;

import java.awt.Color;

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
   * @throws IllegalArgumentException filter kernel matrix is not a rectangular matrix with odd
   *                                  rows and columns.
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
          }
        }
      }
    }

    if (!valid) {
      throw new IllegalArgumentException("Illegal kernel size!");
    }
  }

  /**
   * Transform a pixel of color into another pixel of color, using color transform matrix.
   *
   * @param c               the input pixel of color.
   * @param transformMatrix the color transform matrix.
   * @return a transformed color pixels.
   */
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
    return new Color(result[0], result[1], result[2], c.getAlpha());
  }

  /**
   * Transform a 2D array of color pixels into another 2D array of color pixels. This will be done
   * using a filter kernel.
   *
   * @param original the input 2D array of color pixels
   * @param kernel   the filter kernel
   * @return a filtered image represented by a 2D {@code Array} of {@link Color}.
   */
  public static Color[][] filtering(Color[][] original, double[][] kernel) {
    int height = original.length;
    int width = original[0].length;
    Color[][] filtered = new Color[height][width];
    int kernelHeight = kernel.length;
    int vertiRadius = (kernelHeight - 1) / 2;
    int kernelWidth = kernel[0].length;
    int horizRadius = (kernelWidth - 1) / 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int r = 0;
        int g = 0;
        int b = 0;

        int[][] subImageR = new int[kernelHeight][kernelWidth];
        int[][] subImageG = new int[kernelHeight][kernelWidth];
        int[][] subImageB = new int[kernelHeight][kernelWidth];

        int subImageRow = 0;
        for (int k = i - vertiRadius; k <= i + vertiRadius; k++) {
          if (k < 0) {
            subImageRow++;
            continue;
          }
          if (k > height - 1) {
            break;
          }
          int subImageCol = 0;
          for (int l = j - horizRadius; l <= j + horizRadius; l++) {
            if (l < 0) {
              subImageCol++;
              continue;
            }
            if (l > width - 1) {
              break;
            }
            subImageR[subImageRow][subImageCol] = original[k][l].getRed();
            subImageG[subImageRow][subImageCol] = original[k][l].getGreen();
            subImageB[subImageRow][subImageCol] = original[k][l].getBlue();
            subImageCol++;
          }
          subImageRow++;
        }

        for (int m = 0; m < kernelHeight; m++) {
          for (int n = 0; n < kernelWidth; n++) {
            r += subImageR[m][n] * kernel[m][n];
            g += subImageG[m][n] * kernel[m][n];
            b += subImageB[m][n] * kernel[m][n];
          }
        }

        int[] rgb = OperationUtil.giveValidColorValue(r, g, b);
        filtered[i][j] = new Color(rgb[0], rgb[1], rgb[2], original[i][j].getAlpha());
      }
    }
    return filtered;
  }

  /**
   * To bound the value of color components between 0-255.
   *
   * @param rgb a sequence of value of color components, in an order of rgb(alpha, if provided).
   * @return a list of valid (bounded between 0-255) values for color components.
   */
  public static int[] giveValidColorValue(int... rgb) {
    for (int i = 0; i < rgb.length; i++) {
      rgb[i] = Math.max(0, Math.min(255, rgb[i]));
    }
    return rgb;
  }
}
