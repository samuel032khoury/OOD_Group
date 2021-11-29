package model.image.operation.colortransform.ranged;

import java.util.function.Function;
import model.image.Image;
import model.image.operation.ImageOperation;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;

/**
 * A ranged image operation applies a filter to each pixel in an image based on some properties of
 * the surrounding pixels in a given range. This is done by setting each channel of each pixel to a
 * weighted sum of the surrounding pixel's colors for that same channel.
 */
public class RangedImageOperation implements ImageOperation {

  private final double[][] filter;
  private final int filterHeight;
  private final int filterWidth;

  /**
   * Constructs an abstract ranged image operation.
   *
   * @param filter The filter to apply to each pixel based on the surrounding pixels
   * @throws IllegalArgumentException If the filter has even dimensions OR If the sum of the filter
   *                                  does not equals 1
   */
  public RangedImageOperation(double[][] filter) throws IllegalArgumentException {
    this.filter = filter;
    if (filter.length == 0 || filter[0].length == 0) {
      throw new IllegalArgumentException("Ranged filters cannot have an empty filter");
    }
    this.filterHeight = filter.length;
    this.filterWidth = filter[0].length;

    if (filterHeight % 2 != 1 || filterWidth % 2 != 1) {
      throw new IllegalArgumentException("Ranged filters cannot have even dimensions");
    }

    if (getTotalWeight(filter) != 1.0) {
      throw new IllegalArgumentException("Ranged filters must sum to 1.0.");
    }
  }

  @Override
  public Image apply(Image img) {
    Image finalImage = img.copy();

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel previousPixel = finalImage.getPixel(i, j);
        Pixel[][] surroundingPixels = getSurroundingPixels(img, i, j);
        finalImage.setPixel(i, j, getNewPixel(previousPixel, surroundingPixels));
      }
    }

    return finalImage;
  }

  /**
   * Gets the sum of all weights in the filter.
   *
   * @param filter The filter to weigh
   * @return The sum of all weights in the filter
   */
  static double getTotalWeight(double[][] filter) {
    double sum = 0;
    for (double[] row : filter) {
      for (double v : row) {
        sum += v;
      }
    }
    return sum;
  }

  /**
   * Gets the pixels surrounding (i, j) based on this operation's filter. For example, for a 3x3
   * filter, this method will return a 3x3 array of pixels.
   *
   * @param img the image to get the pixels from
   * @param i   the row position of the central pixel
   * @param j   the col position of the central pixel
   * @return the array of pixels surrounding the central pixel to apply the filter on
   */
  private Pixel[][] getSurroundingPixels(Image img, int i, int j) {
    Pixel[][] surroundingPixels = new Pixel[filterHeight][filterWidth];

    int halfFilterHeight = filterHeight / 2;
    int halfFilterWidth = filterWidth / 2;
    for (int row = i - halfFilterHeight; row <= i + halfFilterHeight; row++) {
      for (int col = j - halfFilterWidth; col <= j + halfFilterWidth; col++) {
        Pixel p;
        try {
          p = img.getPixel(row, col);
        } catch (IndexOutOfBoundsException e) {
          p = new RGBPixel(0, 0, 0);
        }

        surroundingPixels[row - (i - halfFilterHeight)][col - (j - halfFilterWidth)] = p;
      }
    }

    return surroundingPixels;
  }

  /**
   * Applies this operation's filter to the given pixel, using the surrounding values to generate
   * channel values.
   *
   * @param previousPixel     the original pixel to transform
   * @param surroundingPixels the array of surrounding pixels to get a channel sum on
   * @return the new, transformed pixel
   */
  private Pixel getNewPixel(Pixel previousPixel, Pixel[][] surroundingPixels) {
    previousPixel.setBlue(getChannelSum(surroundingPixels, Pixel::getBlue));
    previousPixel.setGreen(getChannelSum(surroundingPixels, Pixel::getGreen));
    previousPixel.setRed(getChannelSum(surroundingPixels, Pixel::getRed));
    return previousPixel;
  }

  /**
   * Gets the sum of the value by applying the given extractor to each surrounding pixel and
   * weighing the value with this operation's filter. Note: assumes surroundingPixels and filter
   * have the same dimensions.
   *
   * @param surroundingPixels the surrounding pixels to apply the filter on
   * @param valueExtractor    the value extractor function to apply on individual pixels
   *                          (channel-specific)
   * @return the channel sum for a specific channel on these pixels
   */
  private int getChannelSum(Pixel[][] surroundingPixels, Function<Pixel, Integer> valueExtractor) {
    int sum = 0;

    for (int i = 0; i < surroundingPixels.length; i++) {
      for (int j = 0; j < surroundingPixels[i].length; j++) {
        sum += valueExtractor.apply(surroundingPixels[i][j]) * filter[i][j];
      }
    }

    return sum;
  }
}
