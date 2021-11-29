package model.image;

import java.util.ArrayList;
import java.util.List;
import model.image.pixel.Pixel;

/**
 * Implements a simple pixel based on the RGB color model (see https://en.wikipedia.org/wiki/RGB_color_model
 * for details).
 */
public class RGBImage implements Image {

  private final List<List<Pixel>> data; // stores the actual image as a matrix of pixels
  private final int width;
  private final int height;

  /**
   * Creates a new RGB-based image given the image name and the data to fill out the image with.
   *
   * @param data the data of the image in pixels
   * @throws IllegalArgumentException If the height or width of the data is not positive.
   */
  public RGBImage(List<List<Pixel>> data) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Cannot construct an RGBImage with null data.");
    }

    this.data = data;
    this.height = data.size();
    if (height == 0) {
      throw new IllegalArgumentException("Cannot construct an RGBImage with height of 0.");
    }

    this.width = data.get(0).size();
    if (width == 0) {
      throw new IllegalArgumentException("Cannot construct an RGBImage with width of 0.");
    }
  }

  @Override
  public Image copy() {
    return new RGBImage(getImageData());
  }

  @Override
  public List<List<Pixel>> getImageData() {
    /*
      We must copy this instead of directly returning data so that data is not modifiable.
     */
    List<List<Pixel>> dataCopy = new ArrayList<>();

    for (List<Pixel> row : data) {
      List<Pixel> rowCopy = new ArrayList<>();
      for (Pixel px : row) {
        rowCopy.add(px.copy());
      }

      dataCopy.add(rowCopy);
    }

    return dataCopy;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public Pixel getPixel(int row, int col) throws IndexOutOfBoundsException {
    validateLocation(row, col);
    return data.get(row).get(col);
  }

  @Override
  public void setPixel(int row, int col, Pixel p) throws IndexOutOfBoundsException {
    validateLocation(row, col);
    data.get(row).set(col, p);
  }

  private void validateLocation(int i, int j) throws IndexOutOfBoundsException {
    if ((i < 0 || i >= height) || (j < 0 || j >= width)) {
      throw new IndexOutOfBoundsException(
          String.format("Cannot get pixel at (%d, %d) [image dimensions=%dx%d]", i, j, width,
              height)
      );
    }
  }
}
