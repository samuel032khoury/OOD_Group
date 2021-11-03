package model.imagefile;

import model.operation.IChannelOperator;

/**
 * To represent an image file that only has RGB value stored and can apply a series of image
 * processing functionalities.
 */
public interface ImageFile extends ReadOnlyImageFile {

  ImageFile vertiFlip();

  ImageFile horizFlip();

  ImageFile brighten(int value);

  ImageFile darken(int value);

  ImageFile greyscale(IChannelOperator operator) throws IllegalArgumentException;

  ImageFile copyImage();
}
