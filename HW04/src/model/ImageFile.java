package model;

public interface ImageFile extends ReadOnlyImageFile{
  ImageFile vertiFlip();
  ImageFile horizFlip() ;
  ImageFile brighten(int value);
  ImageFile darken(int value);
  ImageFile greyscale(IChannelOperator pixelChannel) throws IllegalArgumentException;
  ImageFile copyImage();
}
