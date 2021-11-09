package model.operation.color;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.function.IFilterFunction;
import model.operation.opertor.filter.IFilterOperator;

public class AFilterOperation extends ANoAlphaOperation {
  protected final IFilterOperator filter;
  protected Map<IFilterOperator, IFilterFunction> supportedFilter;

  public AFilterOperation(IFilterOperator filter) {
    this.filter = filter;
    supportedFilter = new HashMap<>();
  }

  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] filtered = new Color[this.height][this.width];
    if (!this.supportedFilter.containsKey(filter)) {
      throw new IllegalStateException("No such an filter can be found!");
    }
    final IFilterFunction function = this.supportedFilter.get(filter);
    //The shape of the kernel has been restricted when it's constructed in enum class
    final double[][] kernel = filter.getKernel();
    int kernelHeight = kernel.length;
    int vertiRadius = (kernelHeight - 1) / 2;
    int kernelWidth = kernel[0].length;
    int horizRadius = (kernelWidth - 1) / 2;
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {

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
          if (k > this.height - 1) {
            break;
          }
          int subImageCol = 0;
          for (int l = j - horizRadius; l <= j + horizRadius; l++) {
            if (l < 0) {
              subImageCol++;
              continue;
            }
            if (l > this.width - 1) {
              break;
            }
            subImageR[subImageRow][subImageCol] = pixels[k][l].getRed();
            subImageG[subImageRow][subImageCol] = pixels[k][l].getGreen();
            subImageB[subImageRow][subImageCol] = pixels[k][l].getBlue();
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

        int[] rgb = OperationUtil.giveValidColorValue((int) r, (int) g, (int) b);
        filtered[i][j] = new Color(rgb[0], rgb[1], rgb[2], pixels[i][j].getAlpha());
      }
    }
    return filtered;
  }
}
