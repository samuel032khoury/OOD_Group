package model.imageFile;

import model.operation.IChannelOperator;

public interface ImageFile extends ReadOnlyImageFile {
  ImageFile vertiFlip();
  ImageFile horizFlip() ;
  ImageFile brighten(int value);
  ImageFile darken(int value);
  ImageFile greyscale(IChannelOperator operator) throws IllegalArgumentException;
  ImageFile copyImage();
}
