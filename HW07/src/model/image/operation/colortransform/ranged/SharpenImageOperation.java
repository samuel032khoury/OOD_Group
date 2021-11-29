package model.image.operation.colortransform.ranged;

/**
 * Defines a sharpen image operation, using the ranged image operation with a default filter.
 */
public class SharpenImageOperation extends RangedImageOperation {

  private static final double[][] SHARPEN_FILTER = new double[][]{
      new double[]{-.125, -.125, -.125, -.125, -.125},
      new double[]{-.125, .25, .25, .25, -.125},
      new double[]{-.125, .25, 1, .25, -.125},
      new double[]{-.125, .25, .25, .25, -.125},
      new double[]{-.125, -.125, -.125, -.125, -.125},
  };

  /**
   * Constructs a sharpen image operation using the ranged image operation abstraction.
   */
  public SharpenImageOperation() throws IllegalArgumentException {
    super(SHARPEN_FILTER);
  }
}
