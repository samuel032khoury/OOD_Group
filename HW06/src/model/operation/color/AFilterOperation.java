package model.operation.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.OperationUtil;
import model.operation.function.IFilterFunction;
import model.operation.opertor.filter.IFilterOperator;

/**
 * An abstract class that has the shared method to process the filtering operations. A map has keys
 * of {@link IFilterFunction} is provided to retrieve appropriate {@link IFilterFunction} applied to
 * the image.
 */
public abstract class AFilterOperation extends ANoAlphaOperation {
  protected final IFilterOperator filter;
  protected final Map<IFilterOperator, IFilterFunction> supportedFilter;

  /**
   * To construct a {@link AFilterOperation} with the {@link IFilterOperator} that saves the filter
   * kernel (as a matrix).
   *
   * @param filter the {@link IFilterOperator} the filter kernel (as a matrix).
   */
  public AFilterOperation(IFilterOperator filter) {
    this.filter = filter;
    supportedFilter = new HashMap<>();
  }

  /**
   * Perform filtering operations on the given {@code pixels}.
   * the rule of filtering is provided by the {@link IFilterFunction},
   * delivered from the {@link #supportedFilter} by searching for the
   * {@link IFilterOperator}
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image being processed
   * @return a filtered 2-D {@code Array} of {@link Color} representing an image, by the
   *         filtering rule provided by {@link IFilterFunction}.
   */
  @Override
  protected Color[][] process(Color[][] pixels) {
    if (!this.supportedFilter.containsKey(filter)) {
      throw new IllegalStateException("No such an filter can be found!");
    }
    final IFilterFunction function = this.supportedFilter.get(filter);
    //The shape of the kernel has been restricted when it's constructed in enum class
    final double[][] kernel = filter.getKernel();
    return function.apply(pixels, kernel);
  }

  /**
   * Transform a 2D array of color pixels into another 2D array of color pixels. This will be done
   * using a filter kernel.
   *
   * @param original the input 2D array of color pixels
   * @param kernel   the filter kernel
   * @return a filtered image represented by a 2D {@code Array} of {@link Color}.
   */
  protected Color[][] filtering(Color[][] original, double[][] kernel) {
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

        int[] rgb = OperationUtil.produceValidColorValue(r, g, b);
        filtered[i][j] = new Color(rgb[0], rgb[1], rgb[2], original[i][j].getAlpha());
      }
    }
    return filtered;
  }
}
