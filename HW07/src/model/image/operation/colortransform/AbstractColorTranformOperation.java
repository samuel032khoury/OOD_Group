package model.image.operation.colortransform;

import model.image.operation.AbstractOperation;

/**
 * The parent class for all the color transformation-based operations.
 */
public abstract class AbstractColorTranformOperation extends AbstractOperation {

  @Override
  protected int getNewRow(int row, int imgWidth, int imgHeight) {
    return row; // color transform has nothing to do with the positioning of the pixel
  }

  @Override
  protected int getNewCol(int col, int imgWidth, int imgHeight) {
    return col; // color transform has nothing to do with the positioning of the pixel
  }
}
