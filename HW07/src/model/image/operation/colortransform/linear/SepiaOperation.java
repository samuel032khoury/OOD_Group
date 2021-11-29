package model.image.operation.colortransform.linear;

/**
 * Performs the sepia transformation on a given image. See https://www.wikiwand.com/en/Sepia_(color)
 * for definition of the sepia color transformation.
 */
public class SepiaOperation extends AbstractLinearColorTransformOperation {

  @Override
  protected double[][] getTransformation() {
    return new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };
  }
}
