package model.imagefile;

import java.awt.*;

import model.operation.ColorTransOperator;
import model.operation.FilterOperator;

public class AImageFilePro extends AImageFile{
  /**
   * To construct an image file that using a 2-D Color array present an image.
   *
   * @param pixels      the 2-D Color array that stores the data information for an image
   * @param maxColorVal the possible maximum value for image's color channel
   */
  public AImageFilePro(Color[][] pixels, int maxColorVal) {
    super(pixels, maxColorVal);
    this.channelOperations.put(FilterOperator.Blur, null);
    this.channelOperations.put(FilterOperator.Sharpening, null);
    this.channelOperations.put(ColorTransOperator.Grayscale, null);
    this.channelOperations.put(ColorTransOperator.Sepia, null);
  }
}
