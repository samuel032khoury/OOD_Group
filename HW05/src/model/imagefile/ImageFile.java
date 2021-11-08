package model.imagefile;

import model.operation.IImageOperation;

/**
 * To represent an image file that only has RGB value stored and can apply a series of image
 * processing functionalities.
 */
public interface ImageFile extends ReadOnlyImageFile {
  ImageFile applyOperation(IImageOperation operation);
//
//  /**
//   * generate a new vertical flipped copy of the image to which this method applies.
//   *
//   * @return a vertical flipped {@link ImageFile}.
//   */
//  T vertiFlip();
//
//  /**
//   * generate a new horizontal flipped copy of the image to which this method applies.
//   *
//   * @return a horizontal flipped {@link ImageFile}.
//   */
//  T horizFlip();
//
//  /**
//   * generate a new brightened copy of the image to which this method applies, with adjusted
//   * magnitude {@code value}.
//   *
//   * @param value the magnitude to adjust
//   * @return a brightened {@link ImageFile} if the value is positive, darkened if it's negative
//   */
//  T brighten(int value);
//
//  /**
//   * generate a new darkened copy of the image to which this method applies, with adjust magnitude
//   * {@code value}.
//   *
//   * @param value the magnitude to adjust
//   * @return a darkened {@link ImageFile} if the value is positive, brightened if it's negative
//   */
//  T darken(int value);
//
//  /**
//   * generate a new greyscale copy of the image to which this method applies, with the provided
//   * {@link IChannelOperator} applied.
//   *
//   * @param operator a {@link IChannelOperator} being applied on the original image.
//   * @return a greyscale {@link ImageFile} derived by applying the provided {@link IChannelOperator}
//   *               to the original image
//   * @throws IllegalArgumentException if the {@link IChannelOperator} is unsupported
//   */
//  T greyscale(IChannelOperator operator) throws IllegalArgumentException;
//
//  /**
//   * generate an identical copy of the image to which this method applies.
//   *
//   * @return an identical {@link ImageFile}.
//   */
//  T copyImage();
}
