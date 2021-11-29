package model.image.operation;

import model.image.Image;
import model.image.pixel.Pixel;

/**
 * Represents an abstract operation.
 */
public abstract class AbstractOperation implements ImageOperation {

  @Override
  public Image apply(Image img) {
    Image finalImage = img.copy();

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel previousPixel = img.getPixel(i, j);

        finalImage.setPixel(getNewRow(i, img.getWidth(), img.getHeight()),
            getNewCol(j, img.getWidth(), img.getHeight()),
            getNewPixel(previousPixel));
      }
    }

    return finalImage;
  }

  /**
   * Gets the new row value for the pixel to be used in a transformation.
   *
   * @param row       the row value to transform
   * @param imgWidth  the width of the image
   * @param imgHeight the height of the image
   * @return the new, transformed row value
   */
  protected abstract int getNewRow(int row, int imgWidth, int imgHeight);

  /**
   * Gets the new col value for the pixel to be used in a transformation.
   *
   * @param col       the column value to transform
   * @param imgWidth  the width of the image
   * @param imgHeight the height of the image
   * @return the new, transformed col value
   */
  protected abstract int getNewCol(int col, int imgWidth, int imgHeight);

  /**
   * Gets the new pixel to be used in a transformation.
   *
   * @param px the pixel to transform
   * @return the new, transformed pixel
   */
  protected abstract Pixel getNewPixel(Pixel px);
}
