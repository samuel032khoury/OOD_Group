package model.image.operation.geometric;

/**
 * Performs a horizontal flip operation on the given image. A horizontal flip acts as a reflection
 * across the vertical axis of the image.
 */
public class HorizontalFlipOperation extends AbstractGeometricOperation {

  @Override
  protected int getNewRow(int row, int imgWidth, int imgHeight) {
    return row;
  }

  @Override
  protected int getNewCol(int col, int imgWidth, int imgHeight) {
    return imgWidth - 1 - col;
  }
}
