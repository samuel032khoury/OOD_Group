package model.image.operation.geometric;

/**
 * Performs a vertical flip operation on the supplied image. A vertical flip acts as a reflection
 * across the horizontal axis of the image.
 */
public class VerticalFlipOperation extends AbstractGeometricOperation {

  @Override
  protected int getNewRow(int row, int imgWidth, int imgHeight) {
    return imgHeight - 1 - row;
  }

  @Override
  protected int getNewCol(int col, int imgWidth, int imgHeight) {
    return col;
  }
}
