package model.operation;

import java.awt.*;

public class OperationUtil {
  public static void checkColorTransformMatrix(double[][] transformMatrix) throws IllegalArgumentException {
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3
            || transformMatrix[1].length != 3 || transformMatrix[2].length != 3) {
      throw new IllegalArgumentException("The Provided Color transformation matrix is invalid!");
    }
  }
}
