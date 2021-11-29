package model.image.operation.colortransform.ranged;

/**
 * Defines a blur image operation, using the ranged image operation with a default filter.
 */
public class BlurImageOperation extends RangedImageOperation {

  private static final double[][] BLUR_FILTER = new double[][]{
      new double[]{.0625, .125, .0625},
      new double[]{.125, .25, .125},
      new double[]{.0625, .125, .0625}
  };

  /**
   * Constructs an abstract ranged image operation with a default filter.
   */
  public BlurImageOperation() {
    super(BLUR_FILTER);
  }
}
